package database;

import game.Player;
import gui.Field;

import java.io.File;

public class Database {
    private static int[] datalevel;
    private static int[] dataplayer;
    private static int[][] field;
    private static int[][] obj;
    //private static ArrayList<Integer> equip;

    public static void init() {
        File file = new File("game.sqlite");
        if (!file.exists()) {
            JDBC.connectDB();
            JDBC.createDB();
            JDBC.insertDB();
        }
    }
    
    public static void setPlayer(String userid)
    {
        String res = JDBC.selectDBplayer(userid);
        int[] resI = strToArr(res);
        datalevel = new int[]{resI[0], resI[1]};
        dataplayer = new int[]{resI[2], resI[3], resI[4], resI[5], resI[6], resI[7],resI[8]};
        String[] res2 = JDBC.selectDBfield(datalevel[1]);
        field = strToArr2(res2[0]);
        obj = strToArr2(res2[1]);
        //equip = new ArrayList<>();        
    }

    public static int[] getDataLevel() {
        return datalevel;
    }

    public static int[] getDataPlayer() {
        return dataplayer;
    }

    public static int[][] getField() {
        return field;
    }

    public static int[][] getObjects() {
        return obj;
    }

    public static void save() {
        String playerS = arrToStr(new int[]{datalevel[0], datalevel[1], dataplayer[0], dataplayer[1], dataplayer[2], dataplayer[3],
                dataplayer[4], dataplayer[5], dataplayer[6]});
        String fieldS = arrToStr2(field);
        String objS = arrToStr2(obj);
        JDBC.savePosDB(playerS, fieldS, objS);
    }

    public static void restore(String userid) {
        String res = JDBC.selectDBplayer(userid);
        int[] resI = strToArr(res);
        datalevel = new int[]{resI[0], resI[1]};
        dataplayer = new int[]{resI[2], resI[3], resI[4], resI[5], resI[6], resI[7],resI[8]};
        String[] res2 = JDBC.selectDBfield(1);
        field = strToArr2(res2[0]);
        obj = strToArr2(res2[1]);
        Player.relocate();
        Field.buffer = null;
    }

    public static void newLevel() {
        try {
            datalevel[1]++;
            String[] res2 = JDBC.selectDBfield(datalevel[1] + 1);
            field = strToArr2(res2[0]);
            obj = strToArr2(res2[1]);
            Field.buffer = null;
        } catch (Exception e) {
            dataplayer[0] = -1;
        }
    }

  private   static int[] strToArr(String str) {
        String[] temp = str.replace("\n", "").substring(1, str.length() - 1).split(",");
        int[] result = new int[temp.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(temp[i]);
        }
        return result;
    }

  private static int[][] strToArr2(String str) {
      
      System.out.println("STR: " + str);
      
        String tmp2 = str.replace("\n", "");
        String tmp3 = tmp2.substring(1, tmp2.length() - 1);
        String[] temp = tmp3.split("@");
        int[][] result = new int[temp.length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = strToArr(temp[i]);
        }
        return result;
    }

  private   static String arrToStr(int[] ar) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < ar.length - 1; i++) {
            sb.append(ar[i]).append(",");
        }
        sb.append(ar[ar.length - 1]).append("]");
        return sb.toString();
    }

   private static String arrToStr2(int[][] ar) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < ar.length - 1; i++) {
            sb.append(arrToStr(ar[i])).append("@");
        }
        sb.append(arrToStr(ar[ar.length - 1])).append("]");
        return sb.toString();
    }

    public static void reset() {
        datalevel = new int[]{1, 1};
        dataplayer = new int[]{0, 0, 0, 10, 10, 10};
        String[] res2 = JDBC.selectDBfield(2);
        field = strToArr2(res2[0]);
        obj = strToArr2(res2[1]);
        //equip = new ArrayList<>();
        save();
    }
}
