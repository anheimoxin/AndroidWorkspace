package com.anheimoxin.chinesechess.local.entity.base;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/*
容器类，用来存储所有需要的绘图信息
 */
public class Container {

    private List<Container> containerList;

    private boolean visible = true;

    public Container() {
        containerList = new ArrayList<Container>();
    }

    public void draw(Canvas canvas) {
        //存储canvas信息
        canvas.save();
        //绘制子类
        drawChild(canvas);
        for (Container child : containerList) {
            if (child.isVisible()) {
                child.draw(canvas);//因为containerList是基类的private成员，子类无法访问，当调用到子类的draw时，containerList为空，所以不会进入死循环。
            }
        }
        //恢复canvas信息
        canvas.restore();
    }

    //子类重写此函数，定制自己的绘制
    public void drawChild(Canvas canvas) {

    }

    public void addChild(Container child) {
        if (child == null) {
            return;
        }
        containerList.add(child);
    }

    public void removeChild(Container child) {
        if (child == null) {
            return;
        }
        containerList.remove(child);
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
