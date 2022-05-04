package com.example.time_paskhin_kislov;

public class TimeSpan {
    private int minutes;
    private int second;
    public boolean Ending;

    public TimeSpan(int second, int minutes) {
        this.Ending=true;
        if (minutes > 60 & second <0 || (minutes==0 && second==0)) {
            throw new IllegalArgumentException("Wrong time format");
        }
        if (second <0 ) {
            throw new IllegalArgumentException("Wrong time format");
        }
        if (second >60)
        {
            this.minutes = (second / 60);
            this.second= second - (minutes*60);
        }
        else
        {
            this.second = second;
            this.minutes = minutes;
        }
    }
    public boolean Until_ending(){
        return this.Ending;
    }

    public void FromSeconds()
    {
        if(this.second<1 & this.minutes>0)
        {
            this.second=59;
            this.minutes-=1;
        }
        else this.second-=1;
        if(this.second==0 & this.minutes==0)
        {
         this.Ending=false;
        }
    }

    public String ToString() {
        return  String.format("%d:%d", minutes, second);
    }

}
