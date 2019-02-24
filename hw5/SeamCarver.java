import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException();
        }
        Color leftColor = picture.get(leftX(x), y);
        Color rightColor = picture.get(rightX(x), y);
        Color upColor = picture.get(x, upY(y));
        Color downColor = picture.get(x, downY(y));
        return Math.pow(leftColor.getRed() - rightColor.getRed(), 2)
                + Math.pow(leftColor.getGreen() - rightColor.getGreen(), 2)
                + Math.pow(leftColor.getBlue() - rightColor.getBlue(), 2)
                + Math.pow(upColor.getRed() - downColor.getRed(), 2)
                + Math.pow(upColor.getGreen() - downColor.getGreen(), 2)
                + Math.pow(upColor.getBlue() - downColor.getBlue(), 2);
    }

    private int leftX(int x) {
        int leftX = x - 1;
        if (leftX < 0) {
            leftX = width - 1;
        }
        return leftX;
    }

    private int rightX(int x) {
        int rightX = x + 1;
        if (rightX >= width) {
            rightX = 0;
        }
        return rightX;
    }

    private int upY(int y) {
        int upY = y + 1;
        if (upY >= height) {
            upY = 0;
        }
        return upY;
    }

    private int downY(int y) {
        int downY = y - 1;
        if (downY < 0) {
            downY = height - 1;
        }
        return downY;
    }

    private void transpose() {
        Picture temp = new Picture(height, width);
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                temp.set(i, j, picture.get(j, i));
            }
        }
        picture = temp;
        int t = width;
        width = height;
        height = t;
    }

    public int[] findHorizontalSeam() {
        transpose();
        int[] result = findVerticalSeam();
        transpose();
        return result;
    }

    public int[] findVerticalSeam() {
        double[][] M = findM();
        double min = Double.MAX_VALUE;
        int start = 0;
        for (int i = 0; i < width; i += 1) {
            if (M[i][height - 1] < min) {
                min = M[i][height - 1];
                start = i;
            }
        }
        return helper(M, start);
    }

    private double[][] findM() {
        double[][] e = new double[width][height];
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                e[i][j] = energy(i, j);
            }
        }
        double[][] M = new double[width][height];
        for (int i = 0; i < width; i += 1) {
            M[i][0] = e[i][0];
        }

        // Special case : width = 1
        if (width == 1) {
            return M;
        }
        // j should be outside, while i should be inside!
        for (int j = 1; j < height; j += 1) {
            for (int i = 0; i < width; i += 1) {
                if (i == 0) {
                    M[i][j] = e[i][j] + Math.min(M[i][j - 1], M[i + 1][j - 1]);
                } else {
                    if (i == width - 1) {
                        M[i][j] = e[i][j] + Math.min(M[i][j - 1], M[i - 1][j - 1]);
                    } else {
                        M[i][j] = e[i][j]
                                + Math.min(Math.min(M[i][j - 1], M[i - 1][j - 1]), M[i + 1][j - 1]);
                    }
                }
            }
        }
        return M;
    }

    private int[] helper(double[][] M, int i) {
        double next;
        int[] path = new int[height];
        path[height - 1] = i;

        // Special case : width = 1
        if (width == 1) {
            for (int j = height - 1; j >= 1; j -= 1) {
                path[j - 1] = i;
            }
            return path;
        }
        for (int j = height - 1; j >= 1; j -= 1) {
            if (i == 0) {
                next = Math.min(M[i][j - 1], M[i + 1][j - 1]);
                if (next == M[i][j - 1]) {
                    path[j - 1] = i;
                } else {
                    path[j - 1] = i + 1;
                }
            } else {
                if (i == width - 1) {
                    next = Math.min(M[i][j - 1], M[i - 1][j - 1]);
                    if (next == M[i][j - 1]) {
                        path[j - 1] = i;
                    } else {
                        path[j - 1] = i - 1;
                    }
                } else {
                    next = Math.min(Math.min(M[i][j - 1], M[i - 1][j - 1]), M[i + 1][j - 1]);
                    if (next == M[i][j - 1]) {
                        path[j - 1] = i;
                    } else {
                        if (next == M[i - 1][j - 1]) {
                            path[j - 1] = i - 1;
                        } else {
                            path[j - 1] = i + 1;
                        }
                    }
                }
            }
            i = path[j - 1];
        }
        return path;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (checkSeam(seam)) {
            picture = new Picture(SeamRemover.removeHorizontalSeam(picture, seam));
            height -= 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void removeVerticalSeam(int[] seam) {
        if (checkSeam(seam)) {
            picture = new Picture(SeamRemover.removeVerticalSeam(picture, seam));
            width -= 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i += 1) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }
        return true;
    }
}
