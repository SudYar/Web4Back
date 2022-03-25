package ru.itmo.services;

import org.springframework.stereotype.Service;
import ru.itmo.model.Dot;
import ru.itmo.dto.PointRequest;
import ru.itmo.dto.PointResponse;

@Service
public class AreaHitChecker {

    public void setIfHit(Dot obj){
        if (obj==null){return;}
        boolean result = false;
        try{
            double x = obj.getX();
            double y = obj.getY();
            double r = obj.getR();
            result = isHit(x, y, r);
        }finally {
            obj.setRes(result);
        }
    }

    public PointResponse getResponse(PointRequest obj){
        if (obj==null){return null;}
        PointResponse response = new PointResponse(obj.getX(), obj.getY(), obj.getR(), false);
        boolean result = false;
        try{
            double x = obj.getX();
            double y = obj.getY();
            double r = obj.getR();
            result = isHit(x, y, r);
        }finally {
            response.setResult(result);
        }
        return response;
    }

    private boolean isHit(double x, double y, double r){
        boolean result = false;
        try{
            int quadrant = detectQuadrant(x, y);
            switch (quadrant){
                case 1:
                    result = ( y < r) && (x < r/2);
                    break;
                case 2:
                    if(r>0){
                        result = x * x + y * y <= r * r / 4;
                    } else {
                        result = y < (x - r/2);
                    }
                    break;
                case 3:
                    result = (y > r) && (x > r/2);
                    break;
                case 4:
                    if(r<0){
                        result = x * x + y * y <= r * r / 4;
                    } else {
                        result = y > (x - r/2);
                    }
                    break;
                default:
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    private int detectQuadrant(double x, double y){
        int res;
        if (x>=0){
            if (y>=0){
                res = 1;
            }else{
                res = 4;
            }
        }else{
            if (y>=0){
                res = 2;
            }else{
                res = 3;
            }
        }
        return res;
    }
}
