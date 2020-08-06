package com.ncx.dms.ui.services;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.ncx.dms.R;
import com.ncx.dms.adapter.SpinnerAdapter;
import com.ncx.dms.core.BaseFragment;
import com.ncx.dms.data.MechanicService;
import com.ncx.dms.data.WarrantyService;
import com.ncx.dms.enums.ApiRequest;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McOrderDetailDto;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.MechanicDto;
import com.ncx.dms.remote.dto.ResultDto;
import com.ncx.dms.remote.dto.UserDto;
import com.ncx.dms.remote.dto.WarrantyRegistrationRequest;
import com.ncx.dms.ui.custom.CustomAutoCompleteTextView;
import com.ncx.dms.ui.custom.CustomEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Response;

public class WarrantyRegisterFragment extends BaseFragment {
    private MechanicService mechanicService;
    private WarrantyService warrantyService;
    private WarrantyViewModel warrantyViewModel;
    //UI__View

    private Spinner spnDealers;
    private Spinner spnMechanics;
    private EditText dpPurchaseDate;
    private CustomAutoCompleteTextView etFrameNo;
    private TextView tvCheckFrameResultMessage;
    private TextView tvProductName;
    private EditText etRegistrationNo;
    private EditText etCustomerName;
    private EditText etCustomerAddress;
    private EditText etCustomerPhone;
    private EditText etWholesaleName;
    private EditText etWholesaleAddress;
    private EditText etWholesalePhone;

    private Button btnSave;

    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_warranty_register, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new WarrantyViewModel(app);
            }
        };
        warrantyViewModel = ViewModelProviders.of(this, factory).get(WarrantyViewModel.class);

        warrantyViewModel.getFormState().observe(getActivity(), new Observer<WarrantyFormState>(){

            @Override
            public void onChanged(WarrantyFormState warrantyFormState) {
                if (warrantyFormState == null) {
                    return;
                }
                btnSave.setEnabled(warrantyFormState.isDataValid());
                if (warrantyFormState.getDealerCodeError() != null) {
                    TextView errorText = (TextView)spnDealers.getSelectedView();
                    //errorText.setError("");
                    //errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    if (errorText != null)
                        errorText.setError(warrantyFormState.getDealerCodeError());
                }
                if (warrantyFormState.getMechanicGuidError() != null) {
                    TextView errorText = (TextView)spnMechanics.getSelectedView();
                    if (errorText != null)
                        errorText.setError(warrantyFormState.getMechanicGuidError());
                }
                if (warrantyFormState.getFrameNoError() != null){
                    etFrameNo.setError(warrantyFormState.getFrameNoError());
                }
                if (warrantyFormState.getRegistrationNoError() != null){
                    etRegistrationNo.setError(warrantyFormState.getRegistrationNoError());
                }
            }
        });

        return view;
    }

    @Override
    public void initService() {
        mechanicService = new MechanicService(app, this);
        warrantyService = new WarrantyService(app, this);
    }

    @Override
    public void findViewById() {

        spnDealers = view.findViewById(R.id.spnDealers);
        spnMechanics = view.findViewById(R.id.spnMechanics);
        dpPurchaseDate = view.findViewById(R.id.dpPurchasedDate);
        dpPurchaseDate.setKeyListener(null);
        etFrameNo = view.findViewById(R.id.etFrameNo);
        tvCheckFrameResultMessage = view.findViewById(R.id.tvCheckFrameResultMessage);
        tvProductName = view.findViewById(R.id.tvProductName);
        etRegistrationNo = view.findViewById(R.id.etKilo);
        etCustomerName = view.findViewById(R.id.etCustomerName);
        etCustomerAddress = view.findViewById(R.id.etCustomerAddress);
        etCustomerPhone = view.findViewById(R.id.etCustomerPhone);
        etWholesaleName = view.findViewById(R.id.etWholesaleName);
        etWholesaleAddress = view.findViewById(R.id.etWholesaleAddress);
        etWholesalePhone = view.findViewById(R.id.etWholesalePhone);

        btnSave = view.findViewById(R.id.btnSave);

        initDatePicker(dpPurchaseDate);
        setViewGone();
    }

    @Override
    public void initListener() {

        etFrameNo.setDrawableClickListener(new CustomEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {

                switch (target) {
                    case RIGHT:
                        verifyFrameNo();
                        break;
                }
            }
        });

        spnDealers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnMechanics.setAdapter(null);
                etFrameNo.setAdapter(null);
                if (view == null)
                    return;

                String dealerCode = view.getTag() == null ? "" : view.getTag().toString();
                formDataChange();
                if (!commonHelper.isNullOrEmpty(dealerCode)) {
                    mechanicService.getMechanicByDealerCode(ApiRequest.GETMECHANICBYDEALERCODE, dealerCode);
                    warrantyService.getAvailableFrmeNos(ApiRequest.GETAVAILABLEFRAMENOs, dealerCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.hashCode() == etFrameNo.getText().hashCode() && etFrameNo.getTag() != null){
                    setViewGone();
                }
                formDataChange();
            }
        };

        spnMechanics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formDataChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etFrameNo.addTextChangedListener(textWatcher);
        etFrameNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                verifyFrameNo();
            }
        });

        etFrameNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && v.getTag() == null)
                    verifyFrameNo();
            }
        });

        etRegistrationNo.addTextChangedListener(textWatcher);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                save();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    @Override
    public void initFillData() {
        List<DealerDto> dealers = ((App)getContext().getApplicationContext()).getCurrentDealers();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for(DealerDto dealerDto : dealers){
            keys.add(dealerDto.getDealerCode());
            values.add(dealerDto.getName());
        }
        SpinnerAdapter adapter =new SpinnerAdapter<>(getContext(), R.layout.custom_spinner, keys, values);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spnDealers.setAdapter(adapter);

        //ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.custom_list_item)
    }

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public List<DealerDto> getCurrentDealers() {
        return null;
    }

    @Override
    public List<McSparePart> getMcSpareParts() {
        return null;
    }

    @Override
    public void apiError(int requestId, String result) {
        showLongToast(result);
    }

    @Override
    public void apiResult(int requestId, Response result) {
        if (requestId == ApiRequest.GETMECHANICBYDEALERCODE){
            ResultDto<List<MechanicDto>> resultDto = (ResultDto<List<MechanicDto>>)result.body();
            List<MechanicDto> mechanics = resultDto.getResultObject();
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            for(MechanicDto mechanicDto : mechanics){
                keys.add(mechanicDto.getMechanicCode());
                values.add(mechanicDto.getName());
            }
            SpinnerAdapter adapter =new SpinnerAdapter<>(getContext(), R.layout.custom_spinner, keys, values);
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            spnMechanics.setAdapter(adapter);
        }else if (requestId == ApiRequest.VERIFYFRAMENO){
            ResultDto<McOrderDetailDto> resultDto = (ResultDto<McOrderDetailDto>)result.body();
            if (resultDto.isSuccess()){
                updateFrameResult(resultDto.getResultObject());
            }else{
                updateFrameResultMessage(true, resultDto.getMessage());
            }

        }else if(requestId == ApiRequest.GETAVAILABLEFRAMENOs){
            ResultDto<List<String>> resultDto = (ResultDto<List<String>>)result.body();
            List<String> frameNos = resultDto.getResultObject();
            String[] array = new String[frameNos.size()];
            frameNos.toArray(array);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, frameNos);
            etFrameNo.setAdapter(adapter);
        }else if (requestId == ApiRequest.WARRANTYREGISTER){
            ResultDto resultDto = (ResultDto)result.body();
            if (resultDto.isSuccess()){
                showLongToast(resultDto.getMessage());
                resetForm();
            }else{
                showLongToast(resultDto.getMessage());
            }
        }
    }

    private void updateFrameResultMessage(boolean isError, String message){
        if (commonHelper.isNullOrEmpty(message)){
            tvCheckFrameResultMessage.setVisibility(View.GONE);
        }else{
            tvCheckFrameResultMessage.setVisibility(View.VISIBLE);
            if (isError){
                tvCheckFrameResultMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                tvCheckFrameResultMessage.setTextColor(Color.RED);
            }else{
                tvCheckFrameResultMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
                tvCheckFrameResultMessage.setTextColor(Color.GREEN);
            }
            tvCheckFrameResultMessage.setText(message);
        }
    }

    private void updateFrameResult(@NonNull McOrderDetailDto orderDetailDto) {
        if (!orderDetailDto.getDealerCode().equalsIgnoreCase(getDealerCode())){
            updateFrameResultMessage(false, "This is not your product!");
        }/*else{
            updateFrameResultMessage(false, orderDetailDto);
        }*/

        tvProductName.setVisibility(View.VISIBLE);
        tvProductName.setText(orderDetailDto.getProductName());
        etFrameNo.setText(orderDetailDto.getFrameNo());
        etFrameNo.setTag(orderDetailDto);

        formDataChange();

        etRegistrationNo.requestFocus();
    }

    private void setViewGone(){
        tvProductName.setVisibility(View.GONE);
        tvCheckFrameResultMessage.setVisibility(View.GONE);

        etFrameNo.setTag(null);
    }

    private void formDataChange(){

        String dealerCode =  getDealerCode();
        String mechanicCode = getMechanicCode();
        String registrationNo = getRegistrationNo();

        warrantyViewModel.warrantyDataChanged(dealerCode, mechanicCode, etFrameNo.getText().toString(), registrationNo, etFrameNo.getTag());
    }

    private void verifyFrameNo(){
        setViewGone();
        if (etFrameNo.getText() == null || commonHelper.isNullOrEmpty(etFrameNo.getText().toString())){
            updateFrameResultMessage(true, "Invalid frame no!");
            return;
        }
        warrantyService.verifyFrameNo(ApiRequest.VERIFYFRAMENO, etFrameNo.getText().toString());
    }

    private void save(){
        if (!validateForm())
            return;

        WarrantyRegistrationRequest request = getRequestFromForm();

        warrantyService.warrantyRegister(ApiRequest.WARRANTYREGISTER, request);
    }

    private void resetForm(){
        setViewGone();
        etFrameNo.setText("");
        etFrameNo.setTag(null);
        etRegistrationNo.setText("");
        etCustomerName.setText("");
        etCustomerPhone.setText("");
        etCustomerAddress.setText("");
        etWholesaleName.setText("");
        etWholesalePhone.setText("");
        etWholesaleAddress.setText("");

        etFrameNo.requestFocus();
    }

    private boolean validateForm(){
        if(commonHelper.isNullOrEmpty(getDealerCode())){
            showLongToast("Please select dealer.");
            spnDealers.requestFocus();
            return false;
        }else if (getMechanicCode() ==  null){
            showLongToast("Please select mechanic.");
            spnMechanics.requestFocus();
            return false;
        }else if (getProductInfo() == null){
            showLongToast("Invalid frame no.");
            etFrameNo.requestFocus();
            return false;
        }else if (commonHelper.isNullOrEmpty(getRegistrationNo())){
            showLongToast("Require registration no.");
            etRegistrationNo.requestFocus();
            return false;
        }
        return true;
    }

    private WarrantyRegistrationRequest getRequestFromForm(){

        McOrderDetailDto productInfo = getProductInfo();
        String dealerCode = getDealerCode();
        String frameNo = productInfo.getFrameNo();
        String registrationNo = getRegistrationNo();
        Date purchasedDate = getPurchasedDate();
        String mechanicCode = getMechanicCode();
        String customerName = getCustomerName();
        String customerAddress = getCustomerAddress();
        String customerPhoneNo = getCustomerPhone();
        boolean isWholesale = isWholesale();
        String wholesaleName = getWholesaleName();
        String wholesaleAddress = getWholesaleAddress();
        String wholesalePhoneNo = getWholesalePhone();

        return new WarrantyRegistrationRequest(dealerCode, frameNo, registrationNo, purchasedDate, mechanicCode, customerName, customerAddress, customerPhoneNo, isWholesale, wholesaleName, wholesaleAddress, wholesalePhoneNo);
    }

    private String getDealerCode(){
        if (spnDealers.getAdapter() == null || spnDealers.getCount() == 0)
            return null;

        if (spnDealers.getSelectedView() == null || spnDealers.getSelectedView().getTag() == null)
            return null;

        return spnDealers.getSelectedView().getTag().toString();
    }

    private McOrderDetailDto getProductInfo(){
        if (etFrameNo.getTag() == null)
            return null;

        return (McOrderDetailDto)etFrameNo.getTag();
    }

    private String getRegistrationNo(){
        if (etRegistrationNo.getText() == null)
            return null;

        return etRegistrationNo.getText().toString();
    }

    private Date getPurchasedDate(){
        if (dpPurchaseDate.getTag() == null)
            return null;

        return ((Date)dpPurchaseDate.getTag());
    }

    private String getMechanicCode(){
        if (spnMechanics.getAdapter() == null || spnMechanics.getCount() == 0)
            return null;

        if (spnMechanics.getSelectedView() == null || spnMechanics.getSelectedView().getTag() == null)
            return null;

        return spnMechanics.getSelectedView().getTag().toString();
    }

    private String getCustomerName(){
        if (etCustomerName.getText() == null || commonHelper.isNullOrEmpty(etCustomerName.getText().toString()))
            return null;

        return etCustomerName.getText().toString();
    }

    private String getCustomerAddress(){
        if (etCustomerAddress.getText() == null || commonHelper.isNullOrEmpty(etCustomerAddress.getText().toString()))
            return null;

        return etCustomerAddress.getText().toString();
    }

    private String getCustomerPhone(){
        if (etCustomerPhone.getText() == null || commonHelper.isNullOrEmpty(etCustomerPhone.getText().toString()))
            return null;

        return etCustomerPhone.getText().toString();
    }

    private String getWholesaleName(){
        if (etWholesaleName.getText() == null || commonHelper.isNullOrEmpty(etWholesaleName.getText().toString()))
            return null;

        return etWholesaleName.getText().toString();
    }

    private String getWholesaleAddress(){
        if (etWholesaleAddress.getText() == null || commonHelper.isNullOrEmpty(etWholesaleAddress.getText().toString()))
            return null;

        return etWholesaleAddress.getText().toString();
    }

    private String getWholesalePhone(){
        if (etWholesalePhone.getText() == null || commonHelper.isNullOrEmpty(etWholesalePhone.getText().toString()))
            return null;

        return etWholesalePhone.getText().toString();
    }

    private boolean isWholesale(){
        if (commonHelper.isNullOrEmpty(getWholesaleName())){
            return false;
        }
        return true;
    }
}
