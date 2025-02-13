import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        System.out.println("Start");
        String server = "https://android-for-students.ru";
        String serverPath = "/materials/practical/hello.php";
        HashMap<String,String> map= new HashMap();
        map.put("name","Smirnov");
        map.put("group","RIBO-01-22");
        HTTPRunnable httpRunnable = new HTTPRunnable(server+serverPath,map);
        Thread th = new Thread(httpRunnable);
        th.start();
        try {
            th.join();
        }catch (InterruptedException e) {
        }finally {
            JSONObject jsonObject = new JSONObject(httpRunnable.getResponseBody());
            int result = jsonObject.getInt("result_code");
            System.out.println("Result: "+result);
            System.out.println("Type: "+jsonObject.getString("message_type"));
            System.out.println("Text: "+jsonObject.getString("message_text"));
            switch (result){
                case 1:
                    JSONArray jsonArray = jsonObject.getJSONArray("task_list");
                    System.out.println("Task list:");
                    for(int i = 0;i<jsonArray.length();i++){
                        System.out.println((i+1)+") "+jsonArray.get(i));
                    }
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }
    }
}
