package engine.io.input.gfxrelated;

import engine.io.input.MyFile;
import engine.math.Vector2f;
import engine.math.Vector3f;

import java.io.IOException;
import java.util.*;

public class OBJFile extends MyFile {
    private static final String FILE_TYPE_LOCK = ".obj";

    private float[] verticesArray;
    private float[] textureCoordsArray;
    private float[] normalsArray;
    private int[] indicesArray;

    public OBJFile(String path) {
        super(path, FILE_TYPE_LOCK);
        Scanner s = null;
        try {
            s = new Scanner(getInputStream());
        } catch (IOException e) {
            System.err.println("File IO Error: File not found!");
            e.printStackTrace();
        }
        String line;
        List<Vertex> vertices = new ArrayList<>();
        List<Vector2f> textureCoords = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        try {
            while (true) {
                line = s.nextLine();
                String[] currentLine = line.split(" ");
                if (currentLine[0].equals("v")) {
                    vertices.add(new Vertex(
                            new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])),
                            vertices.size()));
                } else if (currentLine[0].equals("vt")) {
                    textureCoords.add(new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2])));
                } else if (currentLine[0].equals("vn")) {
                    normals.add(new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])));
                } else if (currentLine[0].equals("f")) {
                    break;
                }
            }
            while (true) {
                if (!line.startsWith("f ")) {
                    line = s.nextLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vextex1 = currentLine[1].split("/");
                String[] vextex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                processVertex(vextex1, vertices, indices);
                processVertex(vextex2, vertices, indices);
                processVertex(vertex3, vertices, indices);
                try {
                    line = s.nextLine();
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            s.close();
        } catch (Exception e) {
            System.err.println("OBJ File Parsing Error: Could not parse OBJ file, check file type or the file's export setting in 3d modeling software.");
            e.printStackTrace();
        }
        removeUnusedVertices(vertices);
        verticesArray = new float[vertices.size() * 3];
        textureCoordsArray = new float[vertices.size() * 2];
        normalsArray = new float[vertices.size() * 3];
        convertDataToArrays(vertices, verticesArray, textureCoords, textureCoordsArray, normals, normalsArray);
        indicesArray = new int[indices.size()];
        for (int i = 0;i < indices.size();i++) {
            indicesArray[i] = indices.get(i);
        }
    }

    private void processVertex(String[] vertexData, List<Vertex> vertices, List<Integer> indices) {
        int vertexPointer = Integer.parseInt(vertexData[0]) - 1;
        Vertex vertex = vertices.get(vertexPointer);
        int textureCoordIndex = Integer.parseInt(vertexData[1]) - 1;
        int normalIndex = Integer.parseInt(vertexData[2]) - 1;
        if (!vertex.hasBeenSet()) {
            vertex.textureCoordIndex = textureCoordIndex;
            vertex.normalIndex = normalIndex;
            indices.add(vertexPointer);
        } else {
            processAlreadyProcessedVertex(vertices, indices, vertex, textureCoordIndex, normalIndex);
        }
    }

    private void processAlreadyProcessedVertex(List<Vertex> vertices, List<Integer> indices, Vertex priorVertex, int newTextureCoordIndex, int newNormalIndex) {
        if (priorVertex.textureCoordIndex == newTextureCoordIndex && priorVertex.normalIndex == newNormalIndex) {
            indices.add(priorVertex.index);
        } else {
            Vertex newVertex = priorVertex.duplicateVertex;
            if (newVertex != null) {
                processAlreadyProcessedVertex(vertices, indices, newVertex, newTextureCoordIndex, newNormalIndex);
            } else {
                newVertex = new Vertex(priorVertex.position, vertices.size());
                newVertex.textureCoordIndex = newTextureCoordIndex;
                newVertex.normalIndex = newNormalIndex;
                priorVertex.duplicateVertex = newVertex;
                vertices.add(newVertex);
                indices.add(newVertex.index);
            }
        }
    }

    private void removeUnusedVertices(List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            if (!vertex.hasBeenSet()) {
                vertex.textureCoordIndex = 0;
                vertex.normalIndex = 0;
            }
        }
    }

    private void convertDataToArrays(List<Vertex> vertices, float[] verticesArray, List<Vector2f> textureCoords, float[] textureCoordsArray, List<Vector3f> normals, float[] normalsArray) {
        for (int i = 0;i < vertices.size();i++) {
            Vertex vertex = vertices.get(i);
            int vertexIndexForVec3 = i * 3;
            int vertexIndexForVec2 = i * 2;
            verticesArray[vertexIndexForVec3] = vertex.position.x;
            verticesArray[vertexIndexForVec3 + 1] = vertex.position.y;
            verticesArray[vertexIndexForVec3 + 2] = vertex.position.z;
            Vector2f textureCoord = textureCoords.get(vertex.textureCoordIndex);
            textureCoordsArray[vertexIndexForVec2] = textureCoord.x;
            textureCoordsArray[vertexIndexForVec2 + 1] = 1 - textureCoord.y;
            Vector3f normal = normals.get(vertex.normalIndex);
            normalsArray[vertexIndexForVec3] = normal.x;
            normalsArray[vertexIndexForVec3 + 1] = normal.y;
            normalsArray[vertexIndexForVec3 + 2] = normal.z;
        }
    }

    public float[] getVerticesArray() {
        return verticesArray;
    }

    public float[] getTextureCoordsArray() {
        return textureCoordsArray;
    }

    public float[] getNormalsArray() {
        return normalsArray;
    }

    public int[] getIndicesArray() {
        return indicesArray;
    }

    private class Vertex {
        private static final int NOT_SET = -1;

        private Vector3f position;
        private int index;
        private int textureCoordIndex = NOT_SET;
        private int normalIndex = NOT_SET;
        private Vertex duplicateVertex = null;

        private Vertex(Vector3f position, int index) {
            this.position = position;
            this.index = index;
        }

        private boolean hasBeenSet() {
            return textureCoordIndex != NOT_SET && normalIndex != NOT_SET;
        }
    }
}
