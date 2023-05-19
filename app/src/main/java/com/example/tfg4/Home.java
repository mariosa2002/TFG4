package com.example.tfg4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Button btnReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        btnReserve = findViewById(R.id.btnReserve);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SportsCenters.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reserves:
                        startActivity(new Intent(getApplicationContext(), Reserves.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), Maps.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.reviews:
                        startActivity(new Intent(getApplicationContext(), Reviews.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Alcántara", "Calle de Alcántara, 26", 140.42807254483631, -3.673507192198761, R.drawable.sc1));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Alfredo Goyeneche", "Calle del Arroyo de Pozuelo, 99", 40.45201978956748, -3.7824983019946443, R.drawable.sc2));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Aluche", "Avenida de las Águilas, 14", 40.38406404633493, -3.7714598750111152, R.drawable.sc3));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Ángel Nieto", "Calle del Payoso Fofó, 22", 40.39721549640839, -3.659894500567898, R.drawable.sc4));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Antiguo Canódromo", "Calle Vía Carpetana, 57", 40.39764806864644, -3.7357407590843947, R.drawable.sc5));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Antonio Díaz Miguel", "Calle Joaquín Dicenta, 1", 40.4713389978715, -3.695424003841762, R.drawable.sc6));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Barajas", "Avenida de Logroño, 70", 40.46283088777632, -3.5872718326776964, R.drawable.sc7));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Barceló", "Calle de Barceló, 6", 40.427081417202785, -3.6988922480214983, R.drawable.sc8));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Blanca Fernández Ochoa", "Calle de Monseñor Óscar Romero, 41", 40.381970796414215, -3.7466117038467046, R.drawable.sc9));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Casa de Campo", "Paseo de la Puerta del Ángel, 7", 40.416257328078245, -3.7334191585310577, R.drawable.sc10));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Centro Integrado Arganzuela", "Calle de Palos de la Frontera, 40", 40.40299718676955, -3.6956085853937704, R.drawable.sc11));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Cerro Almodóvar", "Calle Cerro Almodóvar, 9", 40.382713281758086, -3.6025913875199995, R.drawable.sc12));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Chamartín", "Avenida de Pío XII, 2", 40.461463961191214, -3.6760394190590455, R.drawable.sc13));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Ciudad de los Poetas", "Calle de Antonio Machado, 12", 40.4662069312164, -3.71896158850024, R.drawable.sc14));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Concepción", "Calle de José del Hierro, 5", 40.43760694429114, -3.6492493867728553, R.drawable.sc15));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Daoíz y Velarde", "Plaza de Daoíz y Velarde, 5", 40.40158562742837, -3.6780354191874554, R.drawable.sc16));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal El Espinillo", "Carretera de Villaverde a Vallecas, 19", 40.35679933332434, -3.6865136633670974, R.drawable.sc17));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal El Olivillo", "Calle Olivillo, 4", 40.406712094112265, -3.745934832680779, R.drawable.sc18));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal El Quijote", "Calle de Numancia, 1", 40.453629141095846, -3.71312853404816, R.drawable.sc19));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Entrevías", "Ronda del Sur, 4", 40.378061060172584, -3.6734859038468897, R.drawable.sc20));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Escuelas de San Antón", "Calle de la Farmacia, 13", 40.42420915876526, -3.699288519186209, R.drawable.sc21));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Fabián Roncero", "Avenida Séptima, 68", 40.449224296020596, -3.600347819184839, R.drawable.sc22));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Faustina Valladolid", "Calle de Ladera de los Almendros, 2", 40.40444304018426, -3.6229860136426346, R.drawable.sc23));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Fernando Martín", "Avenida del Santo Ángel de la Guarda, 6", 40.46048887001476, -3.712210203842385, R.drawable.sc24));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Francisco Fernández Ochoa", "Calle Catorce Olivas, 1", 40.365297438888035, -3.7403062596703345, R.drawable.sc25));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Fuente del Berro", "Calle de Elvira, 36", 40.42123003207649, -3.6653140750090913, R.drawable.sc26));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Félix Rubio", "Calle de la Alianza, 4", 40.357339705479745, -3.69163994802536, R.drawable.sc27));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Gallur", "Calle de Gallur, 2", 40.398663812673, -3.7378444038457816, R.drawable.sc28));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Gimnasio Moscardó", "Calle del Pilar de Zaragoza, 93", 40.43813874810246, -3.6745332596663087, R.drawable.sc29));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Hortaleza", "Carretera de la Estación de Hortaleza, 11", 40.479021944699134, -3.652772372570443, R.drawable.sc30));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal José María Cagigal", "Calle de Santa Pola, 22", 40.432432068276384, -3.7364660038439226, R.drawable.sc31));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Juan de Dios Román", "Calle de José Gutiérrez Maroto, 34", 40.36431139094132, -3.6050888191895005, R.drawable.sc32));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Almudena", "Calle de Nicolás Salmerón, 8", 40.41826375913579, -3.627840003844699, R.drawable.sc33));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Bombilla", "Paseo de la Senda del Rey, 8", 40.43279863393086, -3.7318163326793687, R.drawable.sc34));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Cebada", "Plaza de la Cebada, 15", 40.41143209754794, -3.7098750768578275, R.drawable.sc35));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Chopera", "Paseo de Fernán Núñez, 3", 40.41248934294407, -3.686174303845031, R.drawable.sc36));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Elipa", "Calle del Alcalde Garrido Juaristi, 17", 40.41466861307776, -3.655995875009431, R.drawable.sc37));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Fundi", "Calle de Alicante, 14", 40.39468801944599, -3.691085501997784, R.drawable.sc38));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal La Masó", "Calle de la Masó, 80", 40.48396485676337, -3.714752230828329, R.drawable.sc39));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Las Cruces", "Avenida de los Poblados, 72", 40.37905271474715, -3.7565335326823384, R.drawable.sc40));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Los Prunos", "Avenida de los Prunos, 98", 40.45362699730041, -3.6187056615136433, R.drawable.sc41));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Luis Aragonés", "El Provencio, 20", 40.465636433196536, -3.6289305652094406, R.drawable.sc42));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Margot Moles", "Paseo del Polideportivo, 3", 40.409275780266235, -3.6032152173388323, R.drawable.sc43));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal María Jesús Rosa", "Avenida del Monforte de Lemos, 38", 40.479264157797076, -3.70942864744646, R.drawable.sc44));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Marqués de Samaranch", "Paseo Imperial, 20", 40.4077602001887, -3.7173149019970597, R.drawable.sc45));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Miguel Guillén Prim", "Calle de Fuentidueña, 6", 40.37404282474932, -3.6234886572655185, R.drawable.sc46));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Moratalaz", "Calle de Valdebernardo, 2", 40.39852389547346, -3.631479476858541, R.drawable.sc47));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Moscardó", "Calle de Andrés Orteaga, 5", 40.38881048572776, -3.706099996894458, R.drawable.sc48));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Orcasitas", "Avenida de Rafaela Ybarra, 52", 40.375837152291105, -3.712736434530729, R.drawable.sc49));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Orcasur", "Calle de Moreja, 11", 40.368283082708736, -3.698088788505617, R.drawable.sc50));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Pabellón Villa de Madrid", "Calle Brezos, 4", 40.46173609659611, -3.5893064191841315, R.drawable.sc51));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Palomeras", "Calle del Tranvía de Arganda, 4", 40.38262679474297, -3.634169975011201, R.drawable.sc52));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Pepu Hernández", "Avenida de Niza, 8", 40.42793198392234, -3.6061294903505483, R.drawable.sc53));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Peñagrande", "Avenida de Monforte de Lemos, 184", 40.47766953778609, -3.717732355040397, R.drawable.sc54));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Peñuelas", "Calle de Arganda, 25", 40.39922332298496, -3.7048229461748283, R.drawable.sc55));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Plata y Castañar", "Paseo Plata y Castañar, 7", 40.35074802311199, -3.718426246177479, R.drawable.sc56));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Playa Victoria", "Calle de la Hierbabuena, 2", 40.45922857892309, -3.7020664652098043, R.drawable.sc57));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Pradillo", "Calle de Pradillo, 33", 40.44964091037641, -3.67154196151389, R.drawable.sc58));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Pueblo Nuevo", "Calle Francisco Rioja, 14", 40.430309940849504, -3.6384994750085564, R.drawable.sc59));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Raúl González", "Calle de Benimamet, 137", 40.34613734888733, -3.6845024191905154, R.drawable.sc60));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal San Blas", "Calle de Arcos de Jalón, 59", 40.428993078092944, -3.6114017866540475, R.drawable.sc61));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal San Cristóbal", "Plaza Pinazo, 9", 40.342909937473415, -3.6914705903552343, R.drawable.sc62));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal San Fermín", "Calle de San Mario, 18", 40.37544634595147, -3.6927900750116014, R.drawable.sc63));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal San Juan Bautista", "Calle Treviana, 1", 40.448489939521686, -3.658417476855786, R.drawable.sc64));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Santa Ana", "Paseo Alamedillas, 5", 40.4981918397145, -3.6963378136268603, R.drawable.sc65));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Triángulo de Oro", "Calle de Bravo Murillo, 376", 40.46526690903849, -3.6928533345257786, R.drawable.sc66));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Vallecas", "Calle del Arroyo de Olivar, 51", 40.391471157785205, -3.6598382191880168, R.drawable.sc67));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Vallehermoso", "Avenida de Filipinas, 7", 40.441433775641386, -3.711461461514367, R.drawable.sc68));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Vicente del Bosque", "Avenida del Monforte de Lemos, 13", 40.478902601532106, -3.6934764884995324, R.drawable.sc69));
        Maps.global.add(new SportsCenter("Centro Deportivo Municipal Wilfred Agbonavbare", "Calle Reguera de Tomateros, 39", 40.37206887398382, -3.65711888850541, R.drawable.sc70));
    }
}