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
/**
 * Exemplo 4 - como criar ações de repetição a partir de um evento loop. Neste
 * exemplo, você usa o loop para fazer o personagem rotacionar continuamente.
 */
public class HelloLoop extends SimpleApplication {

    public static void main(String[] args) {
        HelloLoop app = new HelloLoop();
        app.start();
    }
    protected Geometry player;

    @Override
    public void simpleInitApp() {
        /** Esta caixa azul é o seu personagem */
        Box b = new Box(1, 1, 1);
        player = new Geometry("blue cube", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);
    }
    
    /* Usa o evento principal de loop para disparar ações de repetição. */
    @Override
    public void simpleUpdate(float tpf){
        //Faz o personagem rotacionar
        player.rotate(0,2*tpf,0);
    }
}
