package org.insset.client.calculator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResetButton;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import org.insset.client.message.dialogbox.DialogBoxInssetPresenter;
import org.insset.client.service.RomanConverterService;
import org.insset.client.service.RomanConverterServiceAsync;
import org.insset.shared.FieldVerifier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author talend
 */
public class CalculatorDecimalPresenter extends Composite {

    @UiField
    public ResetButton boutonClearR;
    @UiField
    public SubmitButton boutonConvertRToA;
    @UiField
    public ResetButton boutonClearA;
    @UiField
    public SubmitButton boutonConvertAToR;
    @UiField
    public ResetButton boutonClearD;
    @UiField
    public SubmitButton boutonConvertD;
    @UiField
    public TextBox valR;
    @UiField
    public TextBox valA;
    @UiField
    public TextBox valD;
    @UiField
    public Label errorLabelRToA;
    @UiField
    public Label errorLabelAToR;
    @UiField
    public Label errorLabelD;


    interface MainUiBinder extends UiBinder<HTMLPanel, CalculatorDecimalPresenter> {
    }

    private static MainUiBinder ourUiBinder = GWT.create(MainUiBinder.class);
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final RomanConverterServiceAsync service = GWT.create(RomanConverterService.class);

    /**
     * Constructeur
     */
    public CalculatorDecimalPresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initHandler();
    }

    /**
     * Init des handler
     */
    private void initHandler() {
        boutonClearR.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valR.setText("");
                errorLabelRToA.setText("");
            }
        });
        boutonConvertRToA.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                convertRomanToArabe();
            }

        });
        boutonClearA.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valA.setText("");
                errorLabelAToR.setText("");
            }
        });
        boutonConvertAToR.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                convertArabeToRoman();
            }

        });
        boutonClearD.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                valD.setText("");
                errorLabelD.setText("");
            }
        });
        boutonConvertD.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                convertDate();
            }

        });
    }

    /**
     * call server
     */
    private void convertRomanToArabe() {
        if (!FieldVerifier.isValidRoman(valR.getText())) {
            errorLabelRToA.addStyleName("serverResponseLabelError");
            errorLabelRToA.setText("Format incorrect");
            return;
        }
        service.convertRomanToArabe(valR.getText(), new AsyncCallback<Integer>() {
            public void onFailure(Throwable caught) {
                // Show the RPC error message to the user
//                Window.alert(SERVER_ERROR);
            }

            public void onSuccess(Integer result) {
                new DialogBoxInssetPresenter("Conversion en chiffres arabes", valR.getText(), String.valueOf(result));
            }
        });
    }

    /**
     * call server
     */
    private void convertArabeToRoman() {
        if (!FieldVerifier.isValidRoman(valA.getText())) {
            errorLabelAToR.addStyleName("serverResponseLabelError");
            errorLabelAToR.setText("Format incorect");

            return ;
        }
        service.convertArabeToRoman(Integer.parseInt(valA.getText()), new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                // Show the RPC error message to the user
//                Window.alert(SERVER_ERROR);
            }

            public void onSuccess(String result) {
                new DialogBoxInssetPresenter("Conversion en chiffres romains", valA.getText(), result);
            }
        });
    }

    /**
     * call server
     */
    private void convertDate() {
        //Verif
        if (!FieldVerifier.isValidDate(valD.getText())) {
            errorLabelAToR.addStyleName("serverResponseLabelError");
            errorLabelAToR.setText("Format incorect");
            return;
        }
        //call server
        service.convertDateYears(valD.getText(), new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                // Show the RPC error message to the user
//                Window.alert(SERVER_ERROR);
            }

            public void onSuccess(String result) {
                new DialogBoxInssetPresenter("Conversion date en chiffres romains", valD.getText(), result);
            }
        });
    }

}
