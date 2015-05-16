/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Thiago Aderaldo Lessa.
 */

/** Classe HelloPicking ensina como colisões entre objetos, capturá-las e adicionar
 uma marca no alvo atingido. */
public class HelloPicking extends SimpleApplication {

    public static void main(String[] args) {
        HelloPicking app = new HelloPicking();
        app.start();
    }
    private Node shootables;
    private Geometry mark;

    @Override
    public void simpleInitApp() {

        initCrossHairs(); //um sinal de + no meio da tele para ajudar como mira
        initKeys(); //carrega um mapa personalizado de teclas
        initMark(); //uma esfera vermelha para marcar o alvo atingido

        /**
         * cria quatro caixas coloridas e um chão para atirar em:
         */
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        shootables.attachChild(makeCube("a Dragon", -2f, 0f, 1f));
        shootables.attachChild(makeCube("a tin can", 1f, -2f, 0f));
        shootables.attachChild(makeCube("the Sheriff", 0f, 1f, -2f));
        shootables.attachChild(makeCube("the Deputy", 1f, 0f, -4f));
        shootables.attachChild(makeFloor());
        shootables.attachChild(makeCharacter());

    }

    protected void initCrossHairs() {
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // sinal de +
        ch.setLocalTranslation( // centro
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    /**
     * Declarando uma ação de "atirar" e mapeando-a ao seu gatilho
     */
    private void initKeys() {

        inputManager.addMapping("Shoot",
                new KeyTrigger(KeyInput.KEY_SPACE),//gatilho 1: barra de espaço
                new MouseButtonTrigger(mouseInput.BUTTON_LEFT)); // gatilho 2: botão esquerdo do mouse
        inputManager.addListener(actionListener, "Shoot");

    }
    /**
     * Definindo a ação "atirar": Determina qual era o alvo e como responder
     */
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {

            if (name.equals("Shoot") && !isPressed) {
                //1. Reiniciar lista de resultados
                CollisionResults results = new CollisionResults();
                //2. Aponta um raio da camera local a camera de direção
                Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                //3. Coleta interseções entre os raios e os alvos em um lista de resultados
                shootables.collideWith(ray, results);
                //4. Exibe os resultados
                System.out.println("----- Colisões? " + results.size() + " -----");
                for (int i = 0; i < results.size(); i++) {
                    //para cada alvo atingido, nós sabemos a distância, o ponto de impacto, o nome da geometria
                    float dist = results.getCollision(i).getDistance();
                    Vector3f pt = results.getCollision(i).getContactPoint();
                    String hit = results.getCollision(i).getGeometry().getName();
                    System.out.println("* Colisão #" + i);
                    System.out.println("  Você atirou " + hit + " a " + pt + ", " + dist + "wu away");
                }
                //5. Usa-se os resultados (marcamos o objeto atingido)
                if (results.size() > 0) {
                    //O alvo fechado da colisão é o verdadeiro acerto
                    CollisionResult closest = results.getClosestCollision();
                    //Hora de interagir - marcamos o alvo com um ponto vermelho
                    mark.setLocalTranslation(closest.getContactPoint());
                    rootNode.attachChild(mark);
                } else {
                    //Nada foi atingido? Então remove-se a marca vermelha
                    rootNode.detachChild(mark);
                }
            }
        }
    };

    private void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }

    protected Geometry makeCube(String name, float x, float y, float z) {
        Box box = new Box(1, 1, 1);
        Geometry cube = new Geometry(name, box);
        cube.setLocalTranslation(x, y, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.randomColor());
        cube.setMaterial(mat1);
        return cube;
    }

    protected Geometry makeFloor() {
        Box box = new Box(15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, -4, -5);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(mat1);
        return floor;
    }

    protected Spatial makeCharacter() {

        // carrega um personagem a partir de jme3test-test-data
        Spatial golem = assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        golem.scale(0.5f);
        golem.setLocalTranslation(-1.0f, -1.5f, -0.6f);

        // adiciona-se luz para tornar o modelo visível.
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        golem.addLight(sun);
        return golem;
    }
}
