package Application;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        choiceThread cT = new choiceThread();
        cT.TOWORK();
    }
}

class choiceThread{

    Random random = new Random();

    // Create object for synchronized bloc
    Object LookToArray1 = new Object();
    Object LookToArray2 = new Object();

    List<Integer> arraylist1 = new ArrayList<>();
    List<Integer> arraylist2 = new ArrayList<>();

    // function for add value in the array
    public void ADDTOARRAY1(){
        synchronized (LookToArray1) {
            arraylist1.add(random.nextInt(1000));
        }
    }

    public void ADDTOARRAY2(){
        synchronized (LookToArray2){
            arraylist2.add(random.nextInt(1000));
        }
    }

    public void ADDVALUE(){
        for(int i = 0; i < 1000; i++){
            ADDTOARRAY1();
            ADDTOARRAY2();
        }
    }

    public void TOWORK(){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ADDVALUE();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ADDVALUE();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            Collections.sort(arraylist1, new SortArray());
            arraylist2.sort((value1, value2)->{
                if(value1 > value2){
                    return -1;
                }else if( value1 < value2){
                    return 1;
                }else{
                    return 0;
                }
            });
            System.out.println(arraylist1);
            System.out.println(arraylist2);
        }
        if(arraylist2.size() == arraylist1.size() && arraylist1.size() == 2000){
            int result1 = 0;
            int result2 = 0;
                for(int i : arraylist1){
                    result1 += i;
                }
                for(int i: arraylist2){
                    result2 +=i;
                }
            if(result1 > result2){
                System.out.println("Array №1 to WIN!");
            }else if(result2 > result1){
                System.out.println("Array №2 to WIN!");
            }else{
                System.out.println("We don't have winner!");
            }
        }else{
            System.out.println("Something went wrong!");
        }

    }

}


class SortArray implements Comparator<Integer>{
    @Override
    public int compare(Integer integer, Integer t1) {
        if(integer > t1){
            return -1;
        }else if(integer < t1){
            return 1;
        }else{
            return 0;
        }
    }
}