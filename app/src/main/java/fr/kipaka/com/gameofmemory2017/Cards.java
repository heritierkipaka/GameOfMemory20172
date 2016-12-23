package fr.kipaka.com.gameofmemory2017;
import android.widget.Button;
/**
 * Created by M0297357 on 22/12/2016.
 */

public class Cards {


    public int x;
    public int y;
    public Button button;

    public Cards(Button button, int x,int y) {
        this.x = x;
        this.y=y;
        this.button=button;
    }


}