package binary;

import java.util.Arrays;

// Problem:
// You are given an array of integers representing books and an integer k representing the number of students.
// The books are arranged in order, and each book has pages[i]. You must allocate books to k students such that each
// student gets a contiguous block of books, and the maximum pages allocated to a student is minimized. 
// Return that minimum value.

// Example:
// Input: pages = [12, 67,34, 90], k = 2
// Possible allocations: 
// [12], [67, 34, 90], max = 191
// [12, 67], [34, 90], max = 124
// [12, 67, 34], [90], max = 113
// Output: 113
// (Allocate [12, 34, 67] and [90] → max = 113)

// Alternate statement:
// You have a fixed, linear sequence of tasks, where the input array T defines the effort required for each task in order.
// You must divide this sequence of tasks among exactly K workers.
//
// The Constraints:
// Each worker must be assigned a contiguous block of tasks (i.e., you cannot skip tasks in the sequence).
// The sum of tasks assigned to any single worker is that worker's total workload.
//
// The Goal: Find the specific way to divide the sequence into K blocks such that the largest total workload carried
// by any single worker is as small as possible. Return this minimized maximum workload.

/**
 * Solves the Book Allocation Problem using Binary Search on the Answer.
 * The goal is to minimize the maximum number of pages allocated to any single student.
 */
public class BookAllocation {

    /**
     * Helper function to determine if it is possible to allocate all books
     * to 'k' students such that no student receives more than 'maxPages' pages.
     *
     * @param maxPages The maximum allowed pages for any single student (the mid value in binary search).
     * @param pages The array of book page counts.
     * @param k The number of students available.
     * @return true if the allocation is feasible, false otherwise.
     */
    private static boolean isFeasible(long maxPages, int[] pages, int k) {
        // If maxPages is less than the pages of the largest book, it's impossible
        // to assign that single book without exceeding maxPages.
        for (int page : pages) {
            if (page > maxPages) {
                return false;
            }
        }

        int studentsRequired = 1;
        long currentPages = 0;

        for (int page : pages) {
            if (currentPages + page <= maxPages) {
                // Add the book to the current student's allocation
                currentPages += page;
            } else {
                // The current student cannot take this book; move to the next student
                studentsRequired++;
                currentPages = page; // The new student starts with this book
            }
        }

        // If the total students required is less than or equal to k, the maximum pages is feasible.
        return studentsRequired <= k;
    }

    /**
     * Finds the minimum value of the maximum pages allocated to any student.
     *
     * @param pages The array of book page counts.
     * @param k The number of students.
     * @return The minimized maximum pages, or -1 if the input is invalid (e.g., k <= 0).
     */
    public static long findMinMaxPages(int[] pages, int k) {
        if (k <= 0) {
            return -1; // Invalid number of students
        }
        if (pages == null || pages.length == 0) {
            return 0; // No books, 0 pages
        }

        // 1. Define the Search Space
        
        // The minimum possible maximum (search space lower bound) is the size of the largest single book.
        long low = 0;
        for (int page : pages) {
            if (page > low) {
                low = page;
            }
        }
        
        // The maximum possible maximum (search space upper bound) is the total number of pages (k=1 case).
        long high = 0;
        for (int page : pages) {
            high += page;
        }
        
        // Handle the edge case where k is greater than the number of books.
        // In this case, each book is assigned to a different student, and the max pages is just the max book size.
        if (k >= pages.length) {
            return low;
        }

        long minMax = high; // Initialize result to the worst-case (total pages)

        // 2. Perform Binary Search
        while (low <= high) {
            long mid = low + (high - low) / 2; // Potential minimum maximum pages

            if (isFeasible(mid, pages, k)) {
                // If 'mid' pages is feasible (we can do it with k students or less):
                // 1. Store 'mid' as a potential answer.
                minMax = mid;
                // 2. Try to find a smaller maximum by shrinking the search space to the left.
                high = mid - 1;
            } else {
                // If 'mid' pages is NOT feasible (we need more than k students):
                // 1. 'mid' is too small; we must allow more pages.
                // 2. Increase the minimum pages limit by shrinking the search space to the right.
                low = mid + 1;
            }
        }

        return minMax;
    }

    public static void main(String[] args) {
        int[] pages1 = {10, 20, 30, 40};
        int k1 = 2;
        // Expected: Student 1 gets [10, 20, 30] = 60, Student 2 gets [40] = 40. Min Max is 60.
        System.out.println("Books: " + Arrays.toString(pages1) + ", Students: " + k1);
        System.out.println("Minimum Max Pages: " + findMinMaxPages(pages1, k1)); // Output: 60

        int[] pages2 = {12, 34, 67, 90};
        int k2 = 3;
        // Expected: [12], [34, 67]=101, [90]. Max is 101.
        // Optimal: [12, 34] = 46, [67], [90]. Max is 90.
        System.out.println("\nBooks: " + Arrays.toString(pages2) + ", Students: " + k2);
        System.out.println("Minimum Max Pages: " + findMinMaxPages(pages2, k2)); // Output: 90

        int[] pages3 = {10, 5, 20, 15};
        int k3 = 1;
        System.out.println("\nBooks: " + Arrays.toString(pages3) + ", Students: " + k3);
        System.out.println("Minimum Max Pages: " + findMinMaxPages(pages3, k3)); // Output: 50 (sum)
        
        int[] pages4 = {10, 5, 20, 15};
        int k4 = 4;
        System.out.println("\nBooks: " + Arrays.toString(pages4) + ", Students: " + k4);
        System.out.println("Minimum Max Pages: " + findMinMaxPages(pages4, k4)); // Output: 20 (max book)
    }
}

