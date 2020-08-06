package com.ncx.dms.ui.services;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.ncx.dms.R;
import com.ncx.dms.adapter.CustomAutoCompleteTextViewAdapter;
import com.ncx.dms.adapter.SparePartItemListViewAdapter;
import com.ncx.dms.adapter.SpinnerAdapter;
import com.ncx.dms.core.BaseFragment;
import com.ncx.dms.core.CommonHelper;
import com.ncx.dms.data.McCatalogService;
import com.ncx.dms.data.MechanicService;
import com.ncx.dms.enums.ApiRequest;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McServiceDetail;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.MechanicDto;
import com.ncx.dms.remote.dto.ResultDto;
import com.ncx.dms.remote.dto.UserDto;
import com.ncx.dms.ui.custom.CustomAutoCompleteTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Response;

public class ServiceRecordFragment extends BaseFragment {

    private MechanicService mechanicService;
    private McCatalogService mcCatalogService;
    private ServiceViewModel serviceViewModel;
    private McServiceDetailViewModel serviceDetailViewModel;

    ArrayList<McServiceDetail> mcServiceDetails;
    CustomAutoCompleteTextViewAdapter partNoAdapter;
    SparePartItemListViewAdapter listViewAdaper;

    //region UI View

    private Spinner spnDealers;
    private Spinner spnMechanics;
    private EditText etCustomerName;
    private EditText etCustomerAddress;
    private EditText etCustomerPhone;
    private EditText dpRepairDate;
    private EditText etFrameNo;
    private EditText etEngineNo;
    private Spinner spnSource;
    private Spinner spnModel;
    private Spinner spnKilometer;
    private Spinner spnRepairType;
    private EditText etRepairDescription;

    private AutoCompleteTextView actvPartNo;
    private TextView tvFullDescription;
    private EditText etPrice;
    private EditText etDiscountPercentage;
    private EditText etQuantity;
    private ListView lvSpareParts;
    private ImageView ivAdd;

    private Button btnSave;

    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service_record, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        //Initialize ViewModel
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ServiceViewModel(app);
            }
        };
        serviceViewModel = ViewModelProviders.of(this, factory).get(ServiceViewModel.class);

        serviceViewModel.getFormState().observe(getActivity(), new Observer<ServiceFormState>(){

            @Override
            public void onChanged(ServiceFormState serviceFormState) {
                if (serviceFormState == null) {
                    return;
                }
                //btnSave.setEnabled(serviceFormState.isDataValid());
                if (serviceFormState.getDealerCodeError() != null) {
                    TextView errorText = (TextView)spnDealers.getSelectedView();
                    //errorText.setError("");
                    //errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    if (errorText != null)
                        errorText.setError(serviceFormState.getDealerCodeError());
                }
                if (serviceFormState.getMechanicGuidError() != null) {
                    TextView errorText = (TextView)spnMechanics.getSelectedView();
                    if (errorText != null)
                        errorText.setError(serviceFormState.getMechanicGuidError());
                }
                if (serviceFormState.getFrameNoError() != null){
                    etFrameNo.setError(serviceFormState.getFrameNoError());
                }
            }
        });

        ViewModelProvider.Factory serviceDetailViewModelfactory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new McServiceDetailViewModel(app);
            }
        };
        serviceDetailViewModel = ViewModelProviders.of(this, serviceDetailViewModelfactory).get(McServiceDetailViewModel.class);
        serviceDetailViewModel.getFormState().observe(getActivity(), new Observer<McServiceDetailFormState>(){

            @Override
            public void onChanged(McServiceDetailFormState mcServiceDetailFormState) {
                if (mcServiceDetailFormState == null) {
                    return;
                }
                ivAdd.setEnabled(mcServiceDetailFormState.isDataValid());
                if (mcServiceDetailFormState.getPartNoError() != null) {
                    actvPartNo.setError(mcServiceDetailFormState.getPartNoError());
                }
                if (mcServiceDetailFormState.getPriceError() != null) {
                    etPrice.setError(mcServiceDetailFormState.getPriceError());
                }
                if (mcServiceDetailFormState.getQuantityError() != null){
                    etQuantity.setError(mcServiceDetailFormState.getQuantityError());
                }
            }
        });

        return view;
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
        }else if (requestId == ApiRequest.GETMCSPAREPARTS){
            ResultDto<List<McSparePart>> resultDto = (ResultDto<List<McSparePart>>)result.body();
            List<McSparePart> mcSpareParts = resultDto.getResultObject();
            partNoAdapter =
                    new CustomAutoCompleteTextViewAdapter(getContext(), mcSpareParts);
            partNoAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            /*List<String> array = new ArrayList<>();
            for(McSparePart item : mcSpareParts){
                array.add(item.getPartNo());
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, array);*/
            actvPartNo.setAdapter(partNoAdapter);

        }
    }

    @Override
    public void apiError(int requestId, String result) {
        showLongToast(result);

    }

    @Override
    public void initService() {
        //Initialize Service
        mechanicService = new MechanicService(app, this);
        mcCatalogService = new McCatalogService(app, this);
    }

    @Override
    public void findViewById() {
        spnDealers = view.findViewById(R.id.spnDealers);
        spnMechanics = (Spinner)view.findViewById(R.id.spnMechanics);
        etCustomerName = view.findViewById(R.id.etCustomerName);
        etCustomerAddress = view.findViewById(R.id.etCustomerAddress);
        etCustomerPhone = view.findViewById(R.id.etCustomerPhone);
        dpRepairDate = view.findViewById(R.id.dpRepairDate);
        dpRepairDate.setKeyListener(null);
        etFrameNo = view.findViewById(R.id.etFrameNo);
        etEngineNo = view.findViewById(R.id.etEngineNo);
        spnSource = view.findViewById(R.id.spnSource);
        spnModel = view.findViewById(R.id.spnModel);
        spnKilometer = view.findViewById(R.id.spnKilometer);
        spnRepairType = view.findViewById(R.id.spnRepairType);
        etRepairDescription = view.findViewById(R.id.etRepairDescription);
        actvPartNo = view.findViewById(R.id.actvPartNo);
        tvFullDescription = view.findViewById(R.id.tvFullDescription);
        etPrice = view.findViewById(R.id.etPrice);
        etDiscountPercentage = view.findViewById(R.id.etDiscountPercentage);
        etQuantity = view.findViewById(R.id.etQuantity);
        ivAdd = view.findViewById(R.id.ivAdd);
        lvSpareParts = view.findViewById(R.id.lvSpareParts);

        btnSave = view.findViewById(R.id.btnSave);

        initDatePicker(dpRepairDate);

        mcServiceDetails = new ArrayList<>();
        listViewAdaper = new SparePartItemListViewAdapter(getContext(), mcServiceDetails);

        lvSpareParts.setAdapter(listViewAdaper);

        clearServiceDetailEntryForm();
    }

    @Override
    public void initListener() {
        spnDealers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnMechanics.setAdapter(null);
                if (view == null)
                    return;

                String dealerCode = view.getTag() == null ? "" : view.getTag().toString();
                formDataChange();
                if (!commonHelper.isNullOrEmpty(dealerCode)) {
                    mechanicService.getMechanicByDealerCode(ApiRequest.GETMECHANICBYDEALERCODE, dealerCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnMechanics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formDataChange();
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
                formDataChange();
            }
        };

        etFrameNo.addTextChangedListener(textWatcher);

        listViewAdaper.setOnClickListener(new SparePartItemListViewAdapter.OnClickListener() {
            @Override
            public void onEdit(int position) {
                McServiceDetail m = listViewAdaper.getItem(position);
                showLongToast(CommonHelper.getCurrencyFormatString(m.getUnitPrice()));
                m.setUnitPrice(1000);
                listViewAdaper.notifyDataSetChanged();
                //showLongToast("Edit");
            }

            @Override
            public void onDelete(int position) {
                showLongToast("Delete");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sample List Item
                McServiceDetail mcServiceDetail = new McServiceDetail();
                mcServiceDetail.setPartNo("Test Part");
                mcServiceDetail.setFullDescription("Test Description");
                mcServiceDetail.setUnitPrice(500.00);
                mcServiceDetail.setQuantity(5);
                mcServiceDetail.setDiscountPercentage(10);
                mcServiceDetail.setGrossAmount(2500);
                mcServiceDetail.setNetAmount(2250);
                mcServiceDetails.add(mcServiceDetail);

               /* McServiceDetail mcServiceDetail1 = new McServiceDetail();
                mcServiceDetail1.setPartNo("Test Part");
                mcServiceDetail1.setFullDescription("Test Description");
                mcServiceDetail1.setUnitPrice(500.00);
                mcServiceDetail1.setQuantity(5);
                mcServiceDetail1.setDiscountPercentage(10);
                mcServiceDetail1.setGrossAmount(2500);
                mcServiceDetail1.setNetAmount(2250);
                mcServiceDetails.add(mcServiceDetail1);*/
                listViewAdaper.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lvSpareParts);

            }
        });

        TextWatcher serviceDetailTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.hashCode() == actvPartNo.getText().hashCode() && actvPartNo.getTag() != null){
                    clearServiceDetailEntryForm();
                }
                formServiceDetailDataChange();
            }
        };

        actvPartNo.addTextChangedListener(serviceDetailTextWatcher);
        etPrice.addTextChangedListener(serviceDetailTextWatcher);
        etQuantity.addTextChangedListener(serviceDetailTextWatcher);

        actvPartNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                McSparePart mcSparePart = partNoAdapter.getItem(position);
                actvPartNo.setTag(mcSparePart);
                enableServiceDetailEntryForm(mcSparePart);
                etPrice.requestFocus();
            }
        });
        actvPartNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && v.getTag() == null)
                    verifyPartNo();
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongToast("you click add");
            }
        });
    }

    @Override
    public void initFillData() {

        //Dealer Spinner
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


        ArrayList<Integer> sourceKeys = new ArrayList<>();
        sourceKeys.add(1);
        sourceKeys.add(2);
        ArrayList<String> sourceValues = new ArrayList<>();
        sourceValues.add("NCX");
        sourceValues.add("Other");

        SpinnerAdapter sourceAdapter =new SpinnerAdapter<>(getContext(), R.layout.custom_spinner, sourceKeys, sourceValues);
        sourceAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spnSource.setAdapter(sourceAdapter);

        ArrayList<String> models = new ArrayList<>();
        models.add("Click");
        models.add("Dream 125");
        models.add("Scoopy");
        models.add("Wave 100");
        models.add("Wave 110i");
        models.add("Wave 110R");
        models.add("Moove");
        models.add("PCX");
        models.add("Other");

        SpinnerAdapter modelAdapter =new SpinnerAdapter<>(getContext(), R.layout.custom_spinner, models, models);
        modelAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spnModel.setAdapter(modelAdapter);

        ArrayList<String> kilometers = new ArrayList<>();
        kilometers.add("1000");/**/
        kilometers.add("4000");
        kilometers.add("8000");
        kilometers.add("12000");
        kilometers.add(">12000");

        SpinnerAdapter kilometerAdapter =new SpinnerAdapter<>(getContext(), R.layout.custom_spinner, kilometers, kilometers);
        kilometerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spnKilometer.setAdapter(kilometerAdapter);

        ArrayList<Integer> repairTypeKeys = new ArrayList<>();
        repairTypeKeys.add(1);
        repairTypeKeys.add(2);
        repairTypeKeys.add(3);

        ArrayList<String> repairTypeValues = new ArrayList<>();
        repairTypeValues.add("PM");
        repairTypeValues.add("GR");
        repairTypeValues.add("Free inspection");

        SpinnerAdapter repairTypeAdapter =new SpinnerAdapter<>(getContext(), R.layout.custom_spinner, repairTypeKeys, repairTypeValues);
        repairTypeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spnRepairType.setAdapter(repairTypeAdapter);

        mcCatalogService.getMcSpareParts(ApiRequest.GETMCSPAREPARTS);
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

    private void clearServiceDetailEntryForm(){
        actvPartNo.setTag(null);
        tvFullDescription.setText("");
        tvFullDescription.setVisibility(View.GONE);
        etPrice.setEnabled(false);
        etPrice.setText("");
        etDiscountPercentage.setEnabled(false);
        etDiscountPercentage.setText("");
        etQuantity.setEnabled(false);
        etQuantity.setText("");
    }

    private void enableServiceDetailEntryForm(@NotNull McSparePart mcSparePart){
        tvFullDescription.setText(mcSparePart.getFullDescription());
        tvFullDescription.setVisibility(View.VISIBLE);
        etPrice.setEnabled(true);
        etDiscountPercentage.setEnabled(true);
        etQuantity.setEnabled(true);

        formServiceDetailDataChange();
    }

    private void formDataChange(){

        String dealerCode =  getDealerCode();
        UUID mechanicId = getMechanicId();
        String frameNo = getFrameNo();

        serviceViewModel.serviceDataChanged(dealerCode, mechanicId, frameNo);
    }

    private void formServiceDetailDataChange(){

        serviceDetailViewModel.mcServiceDetailDataChanged(getPartNo(), getPrice(), getQuantity());
    }

    private void verifyPartNo(){
        if (actvPartNo.getText() == null || CommonHelper.isNullOrEmpty(actvPartNo.getText().toString()))
            return;

        McSparePart mcSparePart = partNoAdapter.getItemByCode(actvPartNo.getText().toString());
        if (mcSparePart != null) {
            actvPartNo.setText(mcSparePart.getPartNo());
            actvPartNo.setTag(mcSparePart);
            enableServiceDetailEntryForm(mcSparePart);
        }
    }

    private String getDealerCode(){
        if (spnDealers.getAdapter() == null || spnDealers.getCount() == 0)
            return null;

        if (spnDealers.getSelectedView() == null || spnDealers.getSelectedView().getTag() == null)
            return null;

        return spnDealers.getSelectedView().getTag().toString();
    }

    private UUID getMechanicId(){
        if (spnMechanics.getAdapter() == null || spnMechanics.getCount() == 0)
            return null;

        if (spnMechanics.getSelectedView() == null || spnMechanics.getSelectedView().getTag() == null)
            return null;

        return (UUID)spnMechanics.getSelectedView().getTag();
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

    private Date getRepairDate(){
        if (dpRepairDate.getTag() == null)
            return null;

        return ((Date)dpRepairDate.getTag());
    }

    private String getFrameNo(){
        if (etFrameNo.getText() == null || commonHelper.isNullOrEmpty(etFrameNo.getText().toString()))
            return null;

        return etFrameNo.getText().toString();
    }

    private String getEngineNo(){
        if (etEngineNo.getText() == null || commonHelper.isNullOrEmpty(etEngineNo.getText().toString()))
            return "-";

        return etEngineNo.getText().toString();
    }

    private Integer getSource(){
        if (spnSource.getAdapter() == null || spnSource.getCount() == 0)
            return null;

        if (spnSource.getSelectedView() == null || spnSource.getSelectedView().getTag() == null)
            return null;

        return (int)spnSource.getSelectedView().getTag();
    }

    private String getModel(){
        if (spnModel.getAdapter() == null || spnModel.getCount() == 0)
            return null;

        if (spnModel.getSelectedView() == null || spnModel.getSelectedView().getTag() == null)
            return null;

        return spnModel.getSelectedView().getTag().toString();
    }

    private String getKilometer(){
        if (spnKilometer.getAdapter() == null || spnKilometer.getCount() == 0)
            return null;

        if (spnKilometer.getSelectedView() == null || spnKilometer.getSelectedView().getTag() == null)
            return null;

        return spnKilometer.getSelectedView().getTag().toString();
    }

    private Integer getRepairType(){
        if (spnRepairType.getAdapter() == null || spnRepairType.getCount() == 0)
            return null;

        if (spnRepairType.getSelectedView() == null || spnRepairType.getSelectedView().getTag() == null)
            return null;

        return (int)spnRepairType.getSelectedView().getTag();
    }

    private String getRepairDescription(){
        if (etRepairDescription.getText() == null || commonHelper.isNullOrEmpty(etRepairDescription.getText().toString()))
            return null;

        return etRepairDescription.getText().toString();
    }

    private String getPartNo(){
        if (actvPartNo.getTag() == null)
            return null;

        return actvPartNo.getText().toString();
    }

    @Nullable
    private Double getPrice(){
        if (etPrice.getText() == null || CommonHelper.isNullOrEmpty(etPrice.getText().toString()))
            return null;

        return CommonHelper.getDobuleFromString(etPrice.getText().toString());
    }

    private Double getDiscount(){
        if (etDiscountPercentage.getText() == null || CommonHelper.isNullOrEmpty(etDiscountPercentage.getText().toString()))
            return 0.00;

        return CommonHelper.getDobuleFromString(etDiscountPercentage.getText().toString());
    }

    @Nullable
    private Integer getQuantity(){
        if (etQuantity.getText() == null || CommonHelper.isNullOrEmpty(etQuantity.getText().toString()))
            return null;

        return CommonHelper.getIntegerFromString(etQuantity.getText().toString());
    }

    /*public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0){
                view.setLayoutParams(new
                        ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }*/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight() * 0.5);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
