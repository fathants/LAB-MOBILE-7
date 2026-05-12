package com.example.praktikum_2;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class PostFragment extends Fragment {

    private ImageView ivSelectImage;
    private EditText etCaption;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivSelectImage.setImageURI(uri);
                    ivSelectImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        ivSelectImage = view.findViewById(R.id.ivSelectImage);
        etCaption = view.findViewById(R.id.etCaption);
        Button btnUpload = view.findViewById(R.id.btnUpload);

        ivSelectImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        btnUpload.setOnClickListener(v -> {
            String caption = etCaption.getText().toString();

            if (selectedImageUri != null) {
                Post newPost = new Post("ira.fitri4343", R.drawable.profile_pic, 0, caption);
                newPost.setImageUri(selectedImageUri.toString());
                DataRepository.userPosts.add(0, newPost);

                Toast.makeText(getActivity(), "Berhasil diunggah", Toast.LENGTH_SHORT).show();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
