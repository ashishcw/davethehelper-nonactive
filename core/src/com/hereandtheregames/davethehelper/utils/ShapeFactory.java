package com.hereandtheregames.davethehelper.utils;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public final class ShapeFactory {

    private ShapeFactory() {
    }

    public static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        return getRectangle(rectangleObject.getRectangle());
    }

    public static PolygonShape getRectangle(Rectangle rectangle) {
        PolygonShape polygon = new PolygonShape();

        Vector2 size = new Vector2(
                Physics.toUnits(rectangle.x + rectangle.width * 0.5f),
                Physics.toUnits(rectangle.y + rectangle.height * 0.5f)
        );

        polygon.setAsBox(
                Physics.toUnits(rectangle.width * 0.5f),
                Physics.toUnits(rectangle.height * 0.5f),
                size,
                0.0f
        );

        return polygon;
    }

    public static CircleShape getCircle(CircleMapObject circleObject) {
        return getCircle(circleObject.getCircle());
    }

    public static CircleShape getCircle(Circle circle) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Physics.toUnits(circle.radius));
        circleShape.setPosition(new Vector2(Physics.toUnits(circle.x), Physics.toUnits(circle.y)));

        return circleShape;
    }

    public static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        return getPolygon(polygonObject.getPolygon());
    }

    public static PolygonShape getPolygon(Polygon polygon) {
        PolygonShape polygonShape = new PolygonShape();

        float[] vertices = polygon.getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = Physics.toUnits(vertices[i]);
        }

        polygonShape.set(worldVertices);

        return polygonShape;
    }

    public static ChainShape getPolyline(PolylineMapObject polylineObject) {
        return getPolyline(polylineObject.getPolyline());
    }

    public static ChainShape getPolyline(Polyline polyline) {
        ChainShape chain = new ChainShape();

        float[] vertices = polyline.getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = Physics.toUnits(vertices[i * 2]);
            worldVertices[i].y = Physics.toUnits(vertices[i * 2 + 1]);
        }

        chain.createChain(worldVertices);

        return chain;
    }
}
