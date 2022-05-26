package com.smile.test;

public class Z {
    /**
     * 解法一：买入消耗一次交易
     */
    public int maxProfit(int k, int[] prices) {
        int len = prices.length;
        if (len <= 0) {
            return 0;
        }

        int[][][] dp = new int[len][k + 1][2];
        // 初始化，j从1开始，[0][0][1]无意义，在for循环中用不到
        for (int j = 1; j <= k; j++) {
            dp[0][j][1] = -prices[0];
        }
        // 无需初始化[i][0][1]，无意义，在for循环中用不到

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][0]);
                // 买入消耗一次交易
                dp[i][j][1] = Math.max(dp[i - 1][j - 1][0] - prices[i], dp[i - 1][j][1]);
            }
        }

        return dp[len - 1][k][0];
    }

    /**
     * 解法二：卖出消耗一次交易
     */
    public int maxProfit2(int k, int[] prices) {
        int len = prices.length;
        if (len <= 0) {
            return 0;
        }

        int[][][] dp = new int[len][k + 1][2];
        // 初始化，j从0开始，因为[0][0][1]有意义，表示第1天买入股票
        for (int j = 0; j <= k; j++) {
            dp[0][j][1] = -prices[0];
        }
        // 初始化，[i][0][1]有意义，表示前i天中有1天买入股票
        for (int i = 1; i < len; i++) {
            dp[i][0][1] = Math.max(dp[i - 1][0][1], -prices[i]);
        }


        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= k; j++) {
                // 卖出消耗一次交易
                dp[i][j][0] = Math.max(dp[i - 1][j - 1][1] + prices[i], dp[i - 1][j][0]);
                dp[i][j][1] = Math.max(dp[i - 1][j][0] - prices[i], dp[i - 1][j][1]);
            }
        }

        return dp[len - 1][k][0];
    }

    public static void main(String[] args) {
        Z z = new Z();
        int i = z.maxProfit2(2, new int[]{3, 2, 6, 5, 0, 3});
        System.out.println(i);
    }
}
