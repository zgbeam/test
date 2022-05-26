package com.smile.test;

/**
 * @author smile
 */
public class Package01 {
    public static void main(String[] args) {
        package01(5, new int[]{0, 3, 6, 3, 8, 6}, new int[]{0, 4, 6, 6, 12, 10}, 10);
    }

    public static void package01(int n, int[] weights, int[] values, int totalWeight) {
        int[][] dp = new int[n + 1][totalWeight + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= totalWeight; j++) {
                if (j < weights[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j - weights[i]] + values[i], dp[i - 1][j]);
                }
            }
        }

        System.out.println("总价值" + dp[n][totalWeight]);
        for (int i = n, j = totalWeight; i > 0; i--) {
            if (dp[i][j] == dp[i - 1][j]) {
                System.out.println(i + " 不选");
            } else {
                j = j - weights[i];
                System.out.println(i + " 选");
            }
        }
    }
}
