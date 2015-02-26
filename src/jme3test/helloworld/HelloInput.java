/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Thiago Aderaldo Lessa
 */
/** Exemplo 5 - como mapear teclas e ações de butoões do mouse */
public class HelloInput extends SimpleApplication{

    public static void main(String[] args){
        HelloInput app = new HelloInput();
        app.start();
    }
    
    protected Geometry player;
    Boolean isRunning = true;
    
    @Override
    public void simpleInitApp() {
    
        Box b = new Box(1,1,1);
        player = new Geometry("Player",b);
        Material mat = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);
        initKeys();
    }
    
    //Customização de teclas: teclas com ações e nomes personalizados
    private void initKeys(){
        
        //Você pode mapear um ou mais entradas a um ação
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT ));
        
        //Adiciona os nomes a lista de ações 
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right","Rotate");     
    }
    
    private ActionListener actionListener = new ActionListener(){
        @Override
        public void onAction(String name, boolean keyPressed, float tpf){
            if(name.equals("Pause") && !keyPressed){
                isRunning = !isRunning;
            }
        }
    };
    
    private AnalogListener analogListener = new AnalogListener(){

        @Override
        public void onAnalog(String name, float value, float tpf) {
        
            if(isRunning){
                if(name.equals("Rotate")){
                    player.rotate(0, value*speed, 0);
                }
                if(name.equals("Right")){
                    Vector3f v = player.getLocalTranslation();
                    player.setLocalTranslation(v.x + value*speed, v.y,v.z);
                }
                if(name.equals("Left")){
                    Vector3f v = player.getLocalTranslation();
                    player.setLocalTranslation(v.x - value*speed, v.y,v.z);
                }
                
            } else{
                System.out.println("Pressione P para continuar");
            }
        }
        
    };
     
}
