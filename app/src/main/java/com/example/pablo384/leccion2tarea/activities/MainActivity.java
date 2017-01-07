package com.example.pablo384.leccion2tarea.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.pablo384.leccion2tarea.R;
import com.example.pablo384.leccion2tarea.adapters.FruitAdapter;
import com.example.pablo384.leccion2tarea.models.Fruit;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listv;
    private GridView gridv;
    private FruitAdapter adapterListView;
    private FruitAdapter adapterGridView;

    //lista de frutas
    private List<Fruit> fruits;

    // Items en el option menu
    private MenuItem itemListView;
    private MenuItem itemGridView;

    // Variables
    private int counter = 0;
    private final int SWITCH_TO_LIST_VIEW = 0;
    private final int SWITCH_TO_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.enforceIconBar();

        this.fruits=getAllFruits();

        listv=(ListView)findViewById(R.id.listView);
        gridv=(GridView)findViewById(R.id.gridView);

        //adjuntamos el metodo clic para ambos
        listv.setOnItemClickListener(this);
        gridv.setOnItemClickListener(this);



        this.adapterListView=new FruitAdapter(this,R.layout.list_view_item_fruit,fruits);
        this.adapterGridView=new FruitAdapter(this, R.layout.grid_view_item_fruit,fruits);

        this.listv.setAdapter(adapterListView);
        this.gridv.setAdapter(adapterGridView);

        //registrar context Menu para ambos
        registerForContextMenu(this.listv);
        registerForContextMenu(this.gridv);




    }

    private void enforceIconBar(){
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clicFruit(fruits.get(position));
    }
    private void clicFruit(Fruit fruit){
        //diferenciamos entre frutas conocidas y desconocidas

        if(fruit.getOrigin().equals("Unknow"))
            Toast.makeText(this,"no hay mas informacion sobre esa fruta",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"La Mejor fruta de "+fruit.getOrigin()+ " es "+fruit.getName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflamos el opcion menu con nuestro layout
        MenuInflater inf=getMenuInflater();
        inf.inflate(R.menu.menu_opcion, menu);
        //despues de inflar recojemos las referencias de los botones que nos interesa
        this.itemListView=menu.findItem(R.id.menuList);
        this.itemGridView=menu.findItem(R.id.menuGrid);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //evento para los clic de botones de menu

        switch (item.getItemId()){
            case R.id.add:
                this.addFruit(new Fruit("Added N"+(++counter), R.mipmap.ic_new_fruit, "Unknow"));
                return true;
            case R.id.menuList:
                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                return true;
            case R.id.menuGrid:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchListGridView(int option){
        //metodo para cambiar entre ListVIew y GridView
        if (option==SWITCH_TO_LIST_VIEW){

            //si queremos cambiar a listview y esta en modo invisible
            if (listv.getVisibility()==View.INVISIBLE) {
                this.gridv.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);

                //no nos olvidemos de ensenar el list vire y ocultar el bt listview dl menu
                this.listv.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }

        }else if (option==SWITCH_TO_GRID_VIEW){
            //si queremos cambiar a gridview y esta invisible
            if (gridv.getVisibility()==View.INVISIBLE){

                this.listv.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(false);

                this.gridv.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(true);
            }else {
                Toast.makeText(this,"Esta funcionando el bt grid",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inf=getMenuInflater();
        //antes de inflar le ponemos el titulo dependiendo de la fruta
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.fruits.get(info.position).getName());

        //inflamos
        inf.inflate(R.menu.menu_context_fruit,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //optener info del contexto menu que se seleccione
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete:
                this.deleteFruit(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private List<Fruit> getAllFruits() {
        List<Fruit> list=new ArrayList<Fruit>() {{
            add(new Fruit("Banana", R.mipmap.ic_banana, "Gran Canaria"));
            add(new Fruit("Strawberry", R.mipmap.ic_strawberry, "Huelva"));
            add(new Fruit("Orange", R.mipmap.ic_orange, "Sevilla"));
            add(new Fruit("Apple", R.mipmap.ic_apple, "Madrid"));
            add(new Fruit("Cherry", R.mipmap.ic_cherry, "Galicia"));
            add(new Fruit("Pear", R.mipmap.ic_pear, "Zaragoza"));
            add(new Fruit("Raspberry", R.mipmap.ic_raspberry, "Barcelona"));
        }
        }; return list;
    }

    private void addFruit(Fruit fruit){
        this.fruits.add(fruit);

        //avisamos dle cambio en ambos adapters
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }
    private void deleteFruit(int position){
        this.fruits.remove(position);

        //avisamos dle cambio en ambos adapters
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }


}
