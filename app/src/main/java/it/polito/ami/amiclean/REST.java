package it.polito.ami.amiclean;

import org.springframework.web.client.RestTemplate;




public class REST {
    public static String StartAndStop = "http://192.168.1.10:8060/api/v1.0/StartAndStop";

    public static boolean turnOn(){
        RestTemplate restTemplate= new RestTemplate();
        int action=1;
        String result = restTemplate.getForObject(StartAndStop + "/" + action,String.class);

        if(result.equals("SUCCESS"))
            return true;
        else
            return false;

    }

    public static boolean turnOff(){
        RestTemplate restTemplate= new RestTemplate();
        int action=0;
        String result = restTemplate.getForObject(StartAndStop + "/" + action,String.class);

        if(result.equals("SUCCESS"))
            return true;
        else
            return false;
    }
    public static String starsUrl="http://192.168.1.10:8060/api/v1.0/Stars";

    public static void sendRating(float stars){
        RestTemplate restTemplate= new RestTemplate();
        restTemplate.delete(starsUrl + "/" + stars);
    }

    public static String updateUrl="http://192.168.1.10:8060/api/v1.0/updateAndroid";


    public static boolean askUpdate(){
        RestTemplate restTemplate = new RestTemplate();
        String state = restTemplate.getForObject(updateUrl,String.class);

        if(state.equals("ON"))
            return true;
        else
            return false;
    }





}
