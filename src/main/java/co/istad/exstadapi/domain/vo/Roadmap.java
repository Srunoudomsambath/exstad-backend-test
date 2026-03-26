package co.istad.exstadapi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Roadmap {
    private List<Node> nodes;
    private List<Edge> edges;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node {
        private String uuid = UUID.randomUUID().toString();
        private String type;
        private NodeData data;
        private Position position;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NodeData {
        private String label;
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private int x;
        private int y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Edge {
        private String id;
        private String source;
        private String target;
        private boolean animated;
    }
}