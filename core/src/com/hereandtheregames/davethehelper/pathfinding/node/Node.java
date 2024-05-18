package com.hereandtheregames.davethehelper.pathfinding.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.hereandtheregames.davethehelper.screens.PlayScreen;
import com.hereandtheregames.davethehelper.utils.GameConfig;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Node extends ScreenAdapter {
    public static final int MAX_ROWS = (int) GameConfig.WORLD_WIDTH_IN_TILES;
    public static final int MAX_COLUMNS = (int) GameConfig.WORLD_WIDTH_IN_TILES;
    //public static Node[][] nodes = new Node[MAX_ROWS][MAX_COLUMNS];
    public static Node[][] nodes = new Node[60][60];

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public enum NodeType{
        start,
        end,
        block,
        path,
        none
    }
    public NodeType nodeType = NodeType.block;

    private int xPosition, yPosition, columnNumber, rowNumber;

    public Node(int xParam, int yParam, int colParam, int rowParam, NodeType nodeTypeParam) {
        this.xPosition = xParam;
        this.yPosition = yParam;
        this.columnNumber = colParam;
        this.rowNumber = rowParam;
        this.nodeType = nodeTypeParam;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public static void initializeNodeGraph(){
        for(int i = 0; i < nodes.length; i++){
            for(int j = 0; j < nodes[i].length; j++){
                nodes[i][j] = new Node(
                        (int)(j*GameConfig.UNIT_SCALE),
                        (int)(i*GameConfig.UNIT_SCALE),
                        (int) (j),
                        (int) (i),
                        NodeType.block
                );
            }
        }
    }


    public static Node getClickedTile(Point clickedPosition){
        Node clickedNode = null;
//        if(!createStartNode && !createBlockedNode && !createEndNode && !createClearNode){
//
//        }
        List<Node> tempList = Arrays.stream(nodes)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        for (Node tempNode : tempList) {
            if(
                    (
                            clickedPosition.getX() >= tempNode.getxPosition()
                                    &&
                            clickedPosition.getX() <= tempNode.getxPosition()+GameConfig.UNIT_SCALE
                    )
                    &&
                    (
                            clickedPosition.getY() >= tempNode.getyPosition()
                            &&
                            clickedPosition.getY() <= tempNode.getyPosition()+GameConfig.UNIT_SCALE
                    )
            ){
                clickedNode = tempNode;
                break;
            }
        }
        return clickedNode;
    }

}
