import java.util.Arrays;

public class NW {

    //This is the filling function for NW. Returns the max score at the given location
    public int score(int[][] m, String s1, String s2, int i, int j) {
        int u_score = m[i - 1][j] - 2;
        int s_score = m[i][j - 1] - 2;

        int d_score = 0;

        if (s1.charAt(j - 1) == s2.charAt(i - 1))
            d_score += m[i - 1][j - 1] + 1;
        else d_score += m[i - 1][j - 1] - 1;
        int[] l = {u_score, s_score, d_score};
        return Arrays.stream(l).max().getAsInt();
    }

    //main function
    public int[][] nw(String s1, String s2) {
        int m = s2.length() + 1;
        int n = s1.length() + 1;

        int mat[][] = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                mat[i][j] = 0;


        //to fill the 1st row and column with multiples of -2
        for (int i = 1; i < m; i++)
            mat[0][i] += mat[0][i - 1] - 2;

        for (int i = 1; i < n; i++)
            mat[i][0] += mat[i - 1][0] - 2;


        //filling loop
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

    //returns the character and the next location to trace at
    public String[] traceback_score(int[][] mat, String s1, String s2, int i, int j) {
        int a = 0, b = 0;
        String sequence="";
        int u_score = mat[i - 1][j];
        int s_score = mat[i][j - 1];

        int max_score = Math.max(u_score, s_score);

        if (s1.charAt(j - 1) == s2.charAt(i - 1)) {
            a = i - 1;
            b = j - 1;
            sequence += s1.charAt(j - 1);
        } else if (max_score == u_score) {
            a = i - 1;
            b = j;
            sequence = "-";
        } else if (max_score == s_score) {
            a = i;
            b = j - 1;
            sequence = "-";
        }

        String[] res={Integer.toString(a),Integer.toString(b),sequence};
        return res;
    }

    public String traceback(int[][] mat,String s1, String s2){
        int m=s2.length();
        int n= s1.length();
        String s="";

        while (m>0 && n>0){
            String[] info=traceback_score(mat,s1,s2,m,n);
            m=Integer.parseInt(info[0]);
            n=Integer.parseInt(info[1]);
            s=s+info[2];
        }
        s=reverse(s);
        System.out.println(s);
        return s;
    }

    private static String reverse(String str){
        char ch[]=str.toCharArray();
        String rev="";
        for(int i=ch.length-1;i>=0;i--){
            rev+=ch[i];
        }
        return rev;
    }

    public static void main(String[] args) {
        NW test = new NW();
        int[][] m = test.nw("ATC", "CAT");
        test.print_mat(m);
        test.traceback(m,"ATC", "CAT");
    }

}
