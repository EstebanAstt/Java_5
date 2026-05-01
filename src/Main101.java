import java.util.ArrayList;

public class Main101 {
    public static void main(String[] args) {
        // expendedor con 5 bebidas de cada tipo y precio $1000
        Expendedor exp = new Expendedor(5, 1000);

        // comprador con una moneda de $1500, quiere cocacola (deposito 0)
        Comprador c = new Comprador(new Moneda1500(), 0, exp);

        // resultados
        System.out.println("Bebiste: " + c.queBebiste());
        System.out.println("Vuelto: " + c.cuantoVuelto());

    }
}









abstract class Bebida {
    private int serie;

    public Bebida(int serie){
        this.serie = serie;
    }

    public int getSerie(){
        return serie;
    }

    public abstract String beber();
}

class Sprite extends Bebida {

    public Sprite(int serie){
        super(serie);
    }

    @Override
    public String beber(){
        return "sprite";
    }
}

class CocaCola extends Bebida {

    public CocaCola(int serie){
        super(serie);
    }

    @Override
    public String beber(){
        return "cocacola";
    }
}










class Deposito<T> { //deposito<T> sirve para crear un deposito generico
    private ArrayList<T> dep;

    public Deposito() {
        this.dep = new ArrayList<T>();
    }

    public void add(T item) {
        dep.add(item);
    }

    public T get() {
        if (dep.size() != 0) {
            return dep.remove(0);
        }
        else{
            return null;
        }
    }
}








abstract class Moneda{
    private int serie;
    private static int s = 0;

    public Moneda(){
        this.serie = s++;
    }

    public Moneda getSerie() {
        return this;
    }

    public abstract int getValor();
}
class Moneda100 extends Moneda {

    public Moneda100(){
        super();
    }
    @Override
    public int getValor(){
        return 100;
    }

}
class Moneda500 extends Moneda {

    public Moneda500(){
        super();
    }
    @Override
    public int getValor(){
        return 500;
    }

}
class Moneda1000 extends Moneda {

    public Moneda1000(){
        super();
    }
    @Override
    public int getValor(){
        return 1000;
    }

}
class Moneda1500 extends Moneda {

    public Moneda1500(){
        super();
    }
    @Override
    public int getValor(){
        return 1500;
    }
}











class Comprador{

    private String sonido;
    private int vuelto;

    public Comprador(Moneda m, int cualBebida, Expendedor exp){
        Bebida b = exp.comprarBebida(m,cualBebida);

        if(b != null){
            this.sonido = b.beber();
        } //sonido
        else{
            this.sonido = null;
        }

        this.vuelto = 0;
        Moneda monVuelto = exp.getVuelto();
        while (monVuelto != null) {
            this.vuelto += monVuelto.getValor();
            monVuelto = exp.getVuelto();
        }
    }

    public int cuantoVuelto() {
        return vuelto;
    }

    public String queBebiste() {
        return sonido;
    }

     //String con el sonido de la Bebida: cocacola, sprite
}





class Expendedor{

    private int numBebidas;
    private int precioBebidas;

    private Deposito<CocaCola> cocacola = new Deposito<>();
    private Deposito<Sprite> sprite = new Deposito<>();
    private Deposito<Moneda> monVu = new Deposito<>();

    public static final int  COCA=1;
    public static final int  SPRITE=2;

    public Expendedor(int numBebidas, int precioBebidas){
        this.numBebidas = numBebidas;
        this.precioBebidas = precioBebidas;


        int cont = 0;
        for(int i = 0; i < numBebidas; i++){
            cocacola.add(new CocaCola(i));
            cont++;
        }
        for(int i = 0; i < numBebidas; i++){
            sprite.add(new Sprite(cont + i));
        }

    }
    public Bebida comprarBebida(Moneda m, int cual){
        if(m == null) return null;
        if(m.getValor() < precioBebidas){
            monVu.add(m);
            return null;
        }
        Bebida b = null;
        if(cual == COCA){
            b = cocacola.get();
        } else if (cual == SPRITE) {
            b = sprite.get();
        }
        else{
            monVu.add(m);
            return null;
        }

        if(b == null){
            monVu.add(m);
            return null;
        }
        //xd

        int diff = m.getValor() - precioBebidas;
        for(int i = 0; i < diff; i+=100){
            monVu.add(new Moneda100());
        }
        return b;

    }

    public Moneda getVuelto() {
        return monVu.get();
    }

}