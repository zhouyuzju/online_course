 import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class testBoard {
    /*
     * hamming = 0 manhattan = 0
     */
    private final int[][] goalBoard = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
    /*
     * hamming = 7 manhattan = 14
     */
    private int[][] testBoardA = { { 4, 5, 6 }, { 3, 0, 1 }, { 2, 8, 7 } };
    // Neighbors of A
    private int[][] testLeftA = { { 4, 5, 6 }, { 0, 3, 1 }, { 2, 8, 7 } };
    private int[][] testRightA = { { 4, 5, 6 }, { 3, 1, 0 }, { 2, 8, 7 } };
    private int[][] testUpA = { { 4, 0, 6 }, { 3, 5, 1 }, { 2, 8, 7 } };
    private int[][] testDownA = { { 4, 5, 6 }, { 3, 8, 1 }, { 2, 0, 7 } };

    /*
     * hamming = 1 manhattan = 1
     */
    private int[][] testBoardB = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };

    private Board A;
    private Board B;
    private Board goal;

    @Before
    public void setUp() throws Exception {
        goal = new Board(goalBoard);
        A = new Board(testBoardA);
        B = new Board(testBoardB);
    }

    @Test
    public void testDimension() {
        assertEquals("Board dimension does not match.",
                goal.dimension(),goalBoard.length);
    }

    @Test
    public void testHamming() {
        assertEquals("goal.hamming() returns wrong value.",0, goal.hamming());
        assertEquals("A.hamming() returns wrong value.", 7 , A.hamming());
        assertEquals("B.hamming() returns wrong value.",1 , B.hamming());
    }

    @Test
    public void testManhattan() {
        assertEquals("goal.manhattan() returns wrong value.",
                goal.manhattan(),0);
        assertEquals("A.manhattan() returns wrong value.",14 , A.manhattan());
        assertEquals("B.manhattan() returns wrong value.",1 , B.manhattan());
    }

    @Test
    public void testIsGoal() {
        assertTrue("Goal board reports that it is not the goal board.",
                goal.isGoal());
        assertFalse("Board A reports that it is the goal board.", A.isGoal());
        assertFalse("Board B reports that it is the goal board.", B.isGoal());
    }

    @Test
    // it is not testing for corner-cases
    // Will not work correctly if equals() is not working
    public void testNeighbors() {
        int counter = 0;
        // Neighbors of A
        Iterator<Board> itA = A.neighbors().iterator();
        Board aLeft = new Board(testLeftA);
        Board aRight = new Board(testRightA);
        Board aUp = new Board(testUpA);
        Board aDown = new Board(testDownA);
        // has left,right,up,down?

        boolean l = false, r = false, u = false, d = false;
        // ----------Test neighbors of A-------
        while (itA.hasNext()) {
            Board b = itA.next();

            // test for 'left'
            if (b.equals(aLeft)) {
                if (l == false) {
                    l = true;
                } else {
                    fail("Multiple 'left' neighbors.");
                }
            }

            // test for 'right'
            if (b.equals(aRight)) {
                if (r == false) {
                    r = true;
                } else {
                    fail("Multiple 'right' neighbors.");
                }
            }

            // test for 'up'
            if (b.equals(aUp)) {
                if (u == false) {
                    u = true;
                } else {
                    fail("Multiple 'up' neighbors.");
                }
            }

            // test for 'down'
            if (b.equals(aDown)) {
                if (d == false) {
                    d = true;
                } else {
                    fail("Multiple 'down' neighbors.");
                }
            }

            // count the number of neighbors
            counter++;

        }

        // check specific neighbors
        assertTrue("There should be a 'left' neighbor.", l);
        assertTrue("There should be a 'right' neighbor.", r);
        assertTrue("There should be an 'up' neighbor.", u);
        assertTrue("There should be a 'down' neighbor.", d);

        // Check the number of neighbors
        assertEquals("The number of neighbors is different than the actual number.",
                4,counter);


    }

    @Test
    public void testNeighboursCC() {
        // Test 1
        int[][] noUpRight = { { 1, 0 }, { 2, 3 } };
        int[][] nleft = { { 0, 1 }, { 2, 3 } };
        int[][] nDown = { { 1, 3 }, { 2, 0 } };
        // Test 2
        int[][] noDownLeft = { { 1, 2 }, { 0, 3 } };
        int[][] nUp = { { 0, 2 }, { 1, 3 } };
        int[][] nRight = { { 1, 2 }, { 3, 0 } };

        // Test 1
        Board b = new Board(noUpRight);
        Board bLeft = new Board(nleft);
        Board bDown = new Board(nDown);
        Iterator<Board> it = b.neighbors().iterator();

        boolean boolLeft = false, boolDown = false;
        int counter = 0;

        while (it.hasNext()) {
            Board x = it.next();
            counter++;

            if (x.equals(bLeft)) {
                if (!boolLeft) {
                    boolLeft = true;
                } else {
                    fail("Multiple \"left\" neighbors.");
                }
            }

            if (x.equals(bDown)) {
                if (!boolDown) {
                    boolDown = true;
                } else {
                    fail("Multiple \"down\" neighbors.");
                }
            }
        }
        assertTrue("There should be a 'down' neighbor.", boolDown == true);
        assertTrue("There should be a 'left' neighbor.", boolLeft == true);
        assertEquals("(Test 1)Wrong number of neighbors returned. ",
                2, counter);

        // Test 2
        b = new Board(noDownLeft);
        Board bRight = new Board(nRight);
        Board bUp = new Board(nUp);
        it = b.neighbors().iterator();

        boolean boolRight = false, boolUp = false;
        counter = 0;

        while (it.hasNext()) {
            Board x = it.next();
            counter++;

            if (x.equals(bRight)) {
                if (!boolRight) {
                    boolRight = true;
                } else {
                    fail("Multiple \"right\" neighbors.");
                }
            }

            if (x.equals(bUp)) {
                if (!boolUp) {
                    boolUp = true;
                } else {
                    fail("Multiple \"up\" neighbors.");
                }
            }
        }
        assertTrue("There should be a 'up' neighbor.", boolUp == true);
        assertTrue("There should be a 'right' neighbor.", boolRight == true);
        assertEquals("(Test 2)Wrong number of neighbors returned: ",
                2,counter);
    }

    @Test
    public void testToString() {
        int dim = -1;
        int[][] array = null;
        Scanner in = new Scanner(A.toString());

        try {
            dim = in.nextInt();
            array = new int[dim][dim];
            for (int i = 0; i < dim * dim; i++) {
                array[i / dim][i % dim] = in.nextInt();
            }
            in.close();
        } catch (Exception e) {
            fail("Reason: " + e.toString());
        }

        Board b = new Board(array);

        assertEquals("The returned dimention does not match " +
                "the actual one.", A.dimension(), dim);
        assertTrue( "The returned string is not a correct " +
                "representation of the board.", A.equals(b));       

    }

    @Test
    public void testEqualsObject() {
        assertFalse("Board report that it is equal to null.", A.equals(null));
        assertTrue("Board report that it is not equal to itself.", A.equals(A));
        assertFalse("Board report that it is equal to non equal board.",
                A.equals(goal));

        Board b = new Board(testBoardA);
        assertTrue("Board report that it is not equal to an equal Board (1).",
                b.equals(A));
        assertTrue("Board report that it is not equal to an equal Board (2).",
                A.equals(b));

    }

    @Test
    // Will not work correctly if equals() is not working
    //If this test fails it may cause other tests that use testBoardB to fail too!
    public void testIsBoardImmutable() {
        Board b = B.twin();
        assertFalse("Calling twin() modifies the original Board.", B.equals(b));

        // modify testBoardB array
        int temp = testBoardB[0][0];
        testBoardB[0][0] = testBoardB[0][1];
        testBoardB[0][1] = temp;

        // create new Board using the modified array
        b = new Board(testBoardB);

        // check if B == b
        assertFalse("Modifying the array, modifies the board.", B.equals(b));

        // restore testBoardB array
        temp = testBoardB[0][0];
        testBoardB[0][0] = testBoardB[0][1];
        testBoardB[0][1] = temp;
    }

    @Test
    // It is my implementation specific
    public void testTwin() {
        // Test 1
        int[][] testBoardATwin = { { 5, 4, 6 }, { 3, 0, 1 }, { 2, 8, 7 } };
        assertTrue("(Test 1)Your twin() is not working correctly!",
                A.twin().equals(new Board(testBoardATwin)));

        // Test 2
        int[][] arrayA = { { 0, 4, 6 }, { 3, 5, 1 }, { 2, 8, 7 } };
        int[][] arrayATwin = { { 0, 4, 6 }, { 5, 3, 1 }, { 2, 8, 7 } };
        Board a = new Board(arrayA);
        Board at = new Board(arrayATwin);

        assertTrue("(Test 2)Your twin() is not working correctly.",
                a.twin().equals(at));

        // test 3
        int[][] arrayB = { { 4, 0, 6 }, { 3, 5, 1 }, { 2, 8, 7 } };
        int[][] arrayBTwin = { { 4, 0, 6 }, { 5, 3, 1 }, { 2, 8, 7 } };
        Board b = new Board(arrayB);
        Board bt = new Board(arrayBTwin);

        assertTrue("(Test 3)Your twin() is not working correctly.",
                b.twin().equals(bt));

    }

}