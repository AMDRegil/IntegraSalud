package com.example.integrasalud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
        //implements PacienteRVAdapter.CourseClickInterface
{
/*
    // creating variables for fab, firebase database,
    // progress bar, list, adapter,firebase auth,
    // recycler view and relative layout.
    private FloatingActionButton addPacienteFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView pacienteRV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<PacienteRVModal> pacienteRVModalArrayList;
    private PacienteRVAdapter pacienteRVAdapter;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // initializing all our variables.
        pacienteRV = findViewById(R.id.idRVPacientes);
        homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addPacienteFAB = findViewById(R.id.idFABAgregarPaciente);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pacienteRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase.getReference("Pacientes");
        // on below line adding a click listener for our floating action button.
        addPacienteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent i = new Intent(MainActivity2.this, AgregarPacienteActivity.class);
                startActivity(i);
            }
        });
        // on below line initializing our adapter class.
        pacienteRVAdapter = new PacienteRVAdapter(pacienteRVModalArrayList, this, this::onCourseClick);
        // setting layout malinger to recycler view on below line.
        pacienteRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        pacienteRV.setAdapter(pacienteRVAdapter);
        // on below line calling a method to fetch courses from database.
        getCourses();
    }

    private void getCourses() {
        // on below line clearing our list.
        pacienteRVModalArrayList.clear();
        // on below line we are calling add child event listener method to read the data.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                pacienteRVModalArrayList.add(snapshot.getValue(PacienteRVModal.class));
                // notifying our adapter that data has changed.
                pacienteRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.setVisibility(View.GONE);
                pacienteRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                pacienteRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                pacienteRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(pacienteRVModalArrayList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // adding a click listner for option selected on below line.
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(getApplicationContext(), "El Usuario ha Salido", Toast.LENGTH_LONG).show();
                // on below line we are signing out our user.
                mAuth.signOut();
                // on below line we are opening our login activity.
                Intent i = new Intent(MainActivity2.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void displayBottomSheet(PacienteRVModal modal) {
        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        // on below line we are inflating our layout file for our bottom sheet.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout);
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        TextView nombreTV = layout.findViewById(R.id.idTVNombre);
        TextView apellidosTV = layout.findViewById(R.id.idTVApellidos);
        TextView edadTV = layout.findViewById(R.id.idTVEdad);
        TextView clinicaTV = layout.findViewById(R.id.idTVClinica);
        TextView pesoTV = layout.findViewById(R.id.idTVPeso);
        TextView alturaTV = layout.findViewById(R.id.idTVAltura);
        TextView actividadTV = layout.findViewById(R.id.idTVActividad);
        TextView idTV = layout.findViewById(R.id.idTVid);
        //ImageView idIV = layout.findViewById(R.id.idIVId);
        // on below line we are setting data to different views on below line.
        nombreTV.setText(modal.getNombre());
        apellidosTV.setText(modal.getApellidos());
        edadTV.setText("Suited for " + modal.getEdad());
        clinicaTV.setText("Rs." + modal.getClinica());
        pesoTV.setText("Rs." + modal.getPeso());
        alturaTV.setText("Rs." + modal.getAltura());
        actividadTV.setText("Rs." + modal.getActividad());
        idTV.setText(modal.getId());
        //Picasso.get().load(modal.getCourseImg()).into(courseIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        // adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(MainActivity2.this, EditarPacienteActivity.class);
                // on below line we are passing our course modal
                i.putExtra("course", modal);
                startActivity(i);
            }
        });
        // adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are navigating to browser
                // for displaying course details from its url
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(modal.getCourseLink()));
                startActivity(i);
            }
        });
    }*/
}