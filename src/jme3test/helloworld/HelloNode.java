/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Thiago Aderaldo Lessa
 */

/**
 * Exemplo 2 - Como utilizar os nós que gerenciam a manipulação de objetos no cenário
 * Você pode rotacionar, movimentar e alterar a escala dos objetos manipulando seus nós pais.
 * O Root Node é especial: Apenas o que é direcionado ao Root Node aparecerá no cenário. *  */
public class HelloNode extends SimpleApplication{

    public static void main(String[] args){
        HelloNode app = new HelloNode();
        app.start();
    }
    @Override
    public void simpleInitApp() {
        
        /** cria um caixa azul nas coordenadas (1,-1,1)*/
        Box box1 = new Box(1, 1, 1);
        Geometry blue = new Geometry("Box", box1);
        blue.setLocalTranslation(new Vector3f(1,-1,1));
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat1);
        
        /** cria uma caixa vermelha logo abaixo da caixa azul, nas coordenadas (1,3,1) */
        Box box2 =  new Box(1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        red.setLocalTranslation(new Vector3f(1, 3, 1));
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);
        
        /** cria um nó pivô no ponto (0,0,0) e o adiciona ao nó root (root node) */
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); //coloca este nó no cenário
        
        /** Adiciona as duas caixas ao nó pivô. E transitivamente ao nó root */
        pivot.attachChild(blue);
        pivot.attachChild(red);
        /** Rotaciona o nó pivô. Note que ambas as caixas são rotacionadas! */
        pivot.rotate(.4f, .4f, 0f);
    }
    
}
