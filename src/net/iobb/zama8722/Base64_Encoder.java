/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iobb.zama8722;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.nio.file.*;
import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author Seiya
 */
public class Base64_Encoder {

    /**
     * 画像をbase64に変換し、クリップボードに貼り付けるプログラムのメインメソッドです。
     * @param args コマンドライン引数（URL）
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String url = args[0];   //コマンドライン引数より画像のURLを取得
        String encoded;
        String extension = getExtension(url);   //URLより、画像の拡張子を取得する
        encoded = Base64_Encoder(url);  //画像のBase64を取得する
        String result = "data:image/" + extension + ";base64," + encoded;   //取得した拡張子と変換結果を使用して、使用可能状態にする。
        setClipboard(result);   //使用可能状態にしたものをクリップボードへ格納
        System.out.println("Base64 Data is setted to Clipboard");   //完了メッセージ
    }
    
    /**
     * 画像ファイルを読み込み、内容をバイト配列に書き込んだものをbase64に変換し、改行コードを取り除き返却するプログラムです。
     * @param url ファイルの場所
     * @return base64に変換し、改行コードを取り除いたもの
     */
    private static String Base64_Encoder(String url){
        byte[] data = null;
        
        //URLをpath型に変換し、そのファイルのバイトを読み取る
        try {
            Path path = Paths.get(url);
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        //バイトを読み取ったものをbase64に変換し、改行コードを取り除き返却
        return Base64.getMimeEncoder().encodeToString(data).replaceAll("[\r\n]", "");
    }
    
    /***
     * ファイルのURLから拡張子のみを取り除くプログラムです。
     * @param url ファイルの場所
     * @return 拡張子
     */
    private static String getExtension(String url){
        int size = url.length();    //urlの文字数を取得
        int start = url.indexOf(".");   //拡張子前の"."が何文字目かを取得
        //"."の次の文字から最後までを切り取り返却
        return url.substring(start + 1, size);
    }
    
    /***
     * 文字列をクリップボードに貼り付けるプログラムです。
     * @param result クリップボードに転送する対象
     */
    private static void setClipboard(String result){
        Toolkit kit = Toolkit.getDefaultToolkit();  //デフォルトのツールキットを返す
        Clipboard cb = kit.getSystemClipboard();    //クリップボードのインスタンス化
        StringSelection ss = new StringSelection(result);   //StringSelectionの実装
        cb.setContents(ss, ss); //クリップボードに貼り付け
    }
}
