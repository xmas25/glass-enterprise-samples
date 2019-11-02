/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.glass.camera2sample;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.glass.camera2sample.GlassGestureDetector.Gesture;
import com.example.glass.camera2sample.GlassGestureDetector.OnGestureListener;
import java.util.Objects;

/**
 * Fragment responsible for displaying the camera preview and handling camera actions.
 */
public class CameraFragment extends Fragment
    implements OnRequestPermissionsResultCallback, OnGestureListener {

  /**
   * Request code for the camera permission. This value doesn't have any special meaning.
   */
  private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 105;

  /**
   * Permissions required for the camera usage.
   */
  private static final String[] REQUIRED_PERMISSIONS = new String[]{permission.CAMERA,
      permission.WRITE_EXTERNAL_STORAGE, permission.RECORD_AUDIO};

  /**
   * Default margin for the shutter indicator.
   */
  private static final int DEFAULT_MARGIN_PX = 8;

  /**
   * An {@link TextureView} for camera preview.
   */
  private TextureView textureView;

  /**
   * An {@link ImageView} for camera shutter image.
   */
  private ImageView shutterImageView;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final FrameLayout frameLayout = new FrameLayout(Objects.requireNonNull(getContext()));
    textureView = new TextureView(getContext());
    frameLayout.addView(textureView);

    shutterImageView = new ImageView(getContext());
    shutterImageView.setImageResource(R.drawable.ic_camera_white_96dp);

    final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
    layoutParams.setMarginEnd(DEFAULT_MARGIN_PX);
    shutterImageView.setLayoutParams(layoutParams);
    frameLayout.addView(shutterImageView);
    return frameLayout;
  }

  @Override
  public void onResume() {
    super.onResume();
    for (String permission : REQUIRED_PERMISSIONS) {
      if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), permission)
          != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{permission}, CAMERA_PERMISSIONS_REQUEST_CODE);
        return;
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
      for (int result : grantResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
          Objects.requireNonNull(getActivity()).finish();
        }
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  public boolean onGesture(Gesture gesture) {
    return false;
  }
}