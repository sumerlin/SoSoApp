package com.billy.android.soso.app.ui.page;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentSecurityBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

import java.io.IOException;
import java.security.GeneralSecurityException;

import rxhttp.wrapper.utils.LogUtil;

/**
 * Author：summer
 * Description：
 */
public class SecurityFragment extends VmDataBindingFragment {
    private SecurityState mState;

    @Override
    protected void initViewModel() {

        mState = getFragmentScopeViewModel(SecurityState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_security, BR.State, mState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentSecurityBinding binding = (FragmentSecurityBinding) getBinding();
        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                securitySP_write();
            }
        });

        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                securitySP_read();
            }
        });
    }

    private void securitySP_write() {
        try {

            String sharedPrefsFile = "securitySP";
            //MasterKeys 相对于一个生成秘钥库，获取公钥mainKeyAlias， 该密钥在 AndroidKeyStore 中生成并存储
            //
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    sharedPrefsFile,
                    mainKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("account", "summer");
            editor.putString("pdw", "123456");
            editor.apply();
            LogUtil.log("======securitySP_write:");

          SharedPreferences sp =   mActivity.getSharedPreferences("sss", MODE_PRIVATE);
            sp.edit().putString("aa", "21").commit();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {

        } finally {

        }
    }

    public void securitySP_read() {
        try {

            String sharedPrefsFile = "securitySP";
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    sharedPrefsFile,
                    mainKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            String account = sharedPreferences.getString("account", "");
            String pwd = sharedPreferences.getString("pwd", "");
            LogUtil.log("======securitySP_read:" + account + pwd);

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {

        } finally {

        }
    }


    public static class SecurityState extends UiStateHolder {

    }
}
