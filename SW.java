import java.util.Arrays;

public class SW {
    public int score(int[][] m, String s1, String s2, int i, int j) {
        int u_score = m[i - 1][j] - 2;
        int s_score = m[i][j - 1] - 2;

        int d_score = 0;

        if (s1.charAt(j - 1) == s2.charAt(i - 1))
            d_score += m[i - 1][j - 1] + 1;
        else
            d_score += m[i - 1][j - 1] - 1;

        int[] l = {u_score, s_score, d_score};
        int m_score =Arrays.stream(l).max().getAsInt();


        if (m_score < 0)
        return 0;
    else
         return m_score;
    }

    public int[][] sw(String s1, String s2) {
        int m = s2.length() + 1;
        int n = s1.length() + 1;

        int mat[][] = new int[m][n];//zeros
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                mat[i][j] = 0;


        for (int i = 1; i < m; i++)
            mat[0][i] = 0;

        for (int i = 1; i < n; i++)
            for (int j = 1; j < n; j++)
                mat[i][j] = score(mat, s1, s2, i, j);
        return mat;
    }

    public void print_mat(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.println();
            for (int j = 0; j < m[0].length; j++)
                System.out.print(m[i][j] + "\t");
        }
        System.out.println();
    }

    public String[] traceback_score(int[][] mat, String s1, String s2, int i, int j) {
        int a = 0, b = 0, s=0;
        String sequence="";

        int u_score = mat[i - 1][j];
        int s_score = mat[i][j - 1];

        int max_score = Math.max(u_score, s_score);

        if (s1.charAt(j - 1) == s2.charAt(i - 1)) {
            a = i - 1;
            b = j - 1;
            sequence += s1.charAt(j - 1);
            s = mat[i-1][j-1];
        } else if (max_score == u_score) {
            a = i - 1;
            b = j;
            sequence += '-';
            s = mat[i-1][j];
        } else if (max_score == s_score) {
            a = i;
            b = j - 1;
            sequence += '-';
            s = mat[i][j-1];
        }

        String[] res={Integer.toString(a),Integer.toString(b),sequence,Integer.toString(s)};
        return res;
    }

    public String traceback(int[][] mat,String s1, String s2){
        int m=s2.length();
        int n= s1.length();
        String s="";

        int a=0,b=0;


        for (int i = 0; i < m+1; i++) {
            for (int j = 0; j < n+1; j++)
                if(mat[i][j]== getMaxValue(mat)){
                    a=i;b=j;
                }
        }

        int breaker=10;

        while (m>0 && n>0 && breaker!=0){
            String[] info=traceback_score(mat,s1,s2,m,n);
            m=Integer.parseInt(info[0]);
            n=Integer.parseInt(info[1]);
            s=s+info[2];
            breaker=Integer.parseInt(info[3]);
        }
        s=reverse(s);
        System.out.println(s);
        return s;
    }

    public static int getMaxValue(int[][] numbers) {
        int maxValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] > maxValue) {
                    maxValue = numbers[j][i];
                }
            }
        }
        return maxValue;
    }

    public static String reverse(String str){
        char ch[]=str.toCharArray();
        String rev="";
        for(int i=ch.length-1;i>=0;i--){
            rev+=ch[i];
        }
        return rev;
    }

    public static void main(String[] args) {
        SW test = new SW();
        int[][] m = test.sw("ATC", "CAT");
        test.print_mat(m);
        test.traceback(m,"ATC", "CAT");
    }
}
