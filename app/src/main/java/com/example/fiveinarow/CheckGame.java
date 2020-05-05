package com.example.fiveinarow;

import android.graphics.Point;

import java.util.List;

public class CheckGame {
    public static final int countMax = 5;

    /**
     * @param points the points located by the player
     * @return whether the player win the game
     */
    public static boolean checkGameWin(List<Point> points) {
        for (Point point: points) {
            int x = point.x;
            int y = point.y;
            boolean horizontalWin = checkHorizontal(x, y, points);
            boolean verticalWin = checkVertical(x, y, points);
            boolean diagonalWin = checkDiagonal(x, y, points);
            if (horizontalWin) {
                return true;
            } else if (verticalWin) {
                return true;
            } else if (diagonalWin) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @param points the points that the player have captured
     * @return if the player have 5 consecutive pieces on a horizontal line
     */
    private static boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        int i = 1;
        int j = 1;
        while (i < countMax) {
            if (x + i > 14) {
                break;
            }
            if (points.contains(new Point(x + i, y))) {
                count++;
                i++;
                continue;
            }
            break;
        }
        if (count >= countMax) {
            return true;
        }
        while(j < countMax) {
            if(x - i < 0) {
                break;
            }
            if (points.contains(new Point(x - j, y))) {
                count++;
                j++;
                continue;
            }
            break;
        }
        if (count >= countMax) {
            return true;
        }
        return false;
    }

    /**
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @param points the points that the player have captured
     * @return if the player have 5 consecutive pieces on a vertical line
     */
    private static boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        int i = 1;
        int j = 1;
        while (i < countMax) {
            if (y + i > 14) {
                break;
            }
            if (points.contains(new Point(x, y + i))) {
                count++;
                i++;
                continue;
            }
            break;
        }

        if (count == countMax) {
            return true;
        }

        while (j < countMax) {
            if (y - j < 0) {
                break;
            }
            if (points.contains(new Point(x, y - j))) {
                count++;
                j++;
                continue;
            }
            break;
        }

        if (count >= countMax) {
            return true;
        }

        return false;
    }

    /**
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @param points the points that the player have captured
     * @return if the player have 5 consecutive pieces on a diagonal line
     */
    private static boolean checkDiagonal(int x, int y, List<Point> points) {
        int leftCount = 1;
        int rightCount = 1;

        for (int i = 1; i < countMax; i++) {
            if (x + i > 14 || y - i < 0) {
                break;
            }
            if (points.contains(new Point(x + i, y - i))){
                leftCount++;
            } else {
                break;
            }
        }

        if (leftCount >= countMax) {
            return true;
        }

        for (int j = 1; j < countMax; j++) {
            if (x - j < 0 || y + j > 14) {
                break;
            }
            if (points.contains(new Point(x - j, y + j))) {
                leftCount++;
            } else {
                break;
            }
        }

        if(leftCount >= countMax) {
            return true;
        }

        for (int i = 1; i < countMax; i++) {
            if (x + i > 14 || y + i > 14) {
                break;
            }
            if (points.contains(new Point(x + i, y + i))){
                rightCount++;
            } else {
                break;
            }
        }

        if (rightCount >= countMax) {
            return true;
        }

        for (int j = 1; j < countMax; j++) {
            if (x - j < 0 || y - j < 0) {
                break;
            }
            if (points.contains(new Point(x - j, y - j))){
                rightCount++;
            } else {
                break;
            }
        }

        if (rightCount >= countMax) {
            return true;
        }

        return false;
    }

}
