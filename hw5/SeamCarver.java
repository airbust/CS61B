import edu.princeton.cs.algs4.Picture;
import java.util.ArrayList;
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
        ArrayList<Integer> seam = new ArrayList<>();
        double totalEnergy = Double.MAX_VALUE;

        for (int i = 0; i < width; i += 1) {
            ArrayList<Integer> tempSeam = new ArrayList<>();
            int x = i;
            double energy = energy(i, 0);
            tempSeam.add(i);

            double topE = 0, leftE = 0, rightE = 0;
            for (int j = 1; j < height; j += 1) {
                int top = x;
                int left = x - 1;
                int right = x + 1;
                topE = energy(top, j);

                if (left >= 0) {
                    leftE = energy(left, j);
                } else {
                    leftE = Double.MAX_VALUE;
                }

                if (right < width) {
                    rightE = energy(right, j);
                } else {
                    rightE = Double.MAX_VALUE;
                }

                if (leftE <= topE && leftE <= rightE) {
                    energy += leftE;
                    tempSeam.add(left);
                    x = left;
                } else {
                    if (topE <= leftE && topE <= rightE) {
                        energy += topE;
                        tempSeam.add(top);
                        x = top;
                    } else {
                        if (rightE <= leftE && rightE <= topE) {
                            energy += rightE;
                            tempSeam.add(right);
                            x = right;
                        }
                    }
                }
            }

            if (energy <= totalEnergy) {
                totalEnergy = energy;
                seam = tempSeam;
            }
        }

        int[] result = new int[seam.size()];
        for (int i = 0; i < seam.size(); i += 1) {
            result[i] = seam.get(i);
        }

        return result;
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
