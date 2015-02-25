/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Thiago Aderaldo Lessa
 */
public class HelloJME3 extends SimpleApplication{

    public static void main(String[] args){
        HelloJME3 app = new HelloJME3();
        app.start();
    }
    @Override
    public void simpleInitApp() {
        Box b = new Box(1,1,1);//cria a forma de um cubo
        Geometry geom = new Geometry("Caixa", b);// cria a geometria de um cubo a partir da forma
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); //cria um material simples
        mat.setColor("Color", ColorRGBA.Blue);//aplicar a cor azul ao material
        geom.setMaterial(mat);//aplica o material ao cubo
        rootNode.attachChild(geom);//faz com que o cubo apareça no cenário.
    }
    
}
