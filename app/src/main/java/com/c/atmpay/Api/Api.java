package com.c.atmpay.Api;

import com.c.atmpay.CetakStruk.ResponCodeSubPS;
import com.c.atmpay.CetakStruk.ResponStruk;
import com.c.atmpay.DaftarHarga.ResponProdukDH;
import com.c.atmpay.DaftarHarga.ResponProdukList;
import com.c.atmpay.DaftarHarga.ResponSubProdukDH;
import com.c.atmpay.Fragment.DirectLink.mDirect;
import com.c.atmpay.Fragment.RekapSaldo.responRekap;
import com.c.atmpay.Fragment.Respon.MRuningText;
import com.c.atmpay.Fragment.RiwayatTransaksi.ResponTransaksi;
import com.c.atmpay.KomisiSales.ResponSales;
import com.c.atmpay.MarkUP.ResponMarkup;
import com.c.atmpay.MarkUP.markupSpesifik.ResponProdukDHM;
import com.c.atmpay.MarkUP.markupSpesifik.ResponProdukListM;
import com.c.atmpay.MarkUP.markupSpesifik.ResponSubProdukDHM;
import com.c.atmpay.MarkUP.sendMarkUP;
import com.c.atmpay.Model.MResestPIN;
import com.c.atmpay.Model.mOTP;
import com.c.atmpay.Model.mResetPassword;
import com.c.atmpay.Notifikasi.Pesan.mPesan;
import com.c.atmpay.Notifikasi.Pesan.mPesanDetail;
import com.c.atmpay.Notifikasi.ResponTransaksiN;
import com.c.atmpay.PengajuanLimit.ResponPengajuan;
import com.c.atmpay.PengajuanLimit.SendPengajuan;
import com.c.atmpay.PersetujuanSaldoSales.ResponPersetujuan;
import com.c.atmpay.PersetujuanSaldoSales.ResponPersetujuanSaldo;
import com.c.atmpay.PersetujuanSaldoSales.SendDataPersetujuan;
import com.c.atmpay.Profil.MPin;
import com.c.atmpay.Profil.MProfilEdit;
import com.c.atmpay.Profil.Poin.mExcange;
import com.c.atmpay.Profil.Poin.mReward;
import com.c.atmpay.Profil.ResEdit;
import com.c.atmpay.Respon.Respon;
import com.c.atmpay.Respon.ResponBanner;
import com.c.atmpay.Respon.ResponEditKec;
import com.c.atmpay.Respon.ResponEditLokasi;
import com.c.atmpay.Respon.ResponEditPost;
import com.c.atmpay.Respon.ResponEditkel;
import com.c.atmpay.Respon.ResponK;
import com.c.atmpay.Respon.ResponKEditKab;
import com.c.atmpay.Respon.ResponKe;
import com.c.atmpay.Respon.ResponMenu;
import com.c.atmpay.Respon.ResponMenuUtama;
import com.c.atmpay.Respon.ResponPost;
import com.c.atmpay.Respon.ResponProfil;
import com.c.atmpay.Respon.ResponResetPassword;
import com.c.atmpay.Respon.ResponResetPin;
import com.c.atmpay.Respon.ResponSubCategory;
import com.c.atmpay.Respon.ResponSubP;
import com.c.atmpay.Respon.Responkel;
import com.c.atmpay.Model.MOtpVerif;
import com.c.atmpay.Model.MRegisData;
import com.c.atmpay.Model.MRegister;
import com.c.atmpay.Model.Mlogin;
import com.c.atmpay.Model.Mphone;
import com.c.atmpay.Model.MsetPIN;
import com.c.atmpay.Model.Responphoto;
import com.c.atmpay.SaldoServer.AddUPP;
import com.c.atmpay.SaldoServer.ResponTagihanPayLatter;
import com.c.atmpay.SaldoServer.ResponUPP;
import com.c.atmpay.SaldoServer.Responn;
import com.c.atmpay.TagihanKonter.ResponApprove;
import com.c.atmpay.TagihanKonter.ResponTagihanKonter;
import com.c.atmpay.TagihanKonter.SendApprove;
import com.c.atmpay.TambahKonter.ResponTambahKonter;
import com.c.atmpay.TambahKonter.SendDataKonter;
import com.c.atmpay.TarikKomisi.ResponKomisi;
import com.c.atmpay.TarikKomisi.mKomisi;
import com.c.atmpay.TopUpSaldoku.HistoryTopUp.ResponHistory;
import com.c.atmpay.TopUpSaldoku.ReqSaldoku;
import com.c.atmpay.TopUpSaldoku.ResponTopUp;
import com.c.atmpay.TopUpSaldoku.mCancel;
import com.c.atmpay.Transaksi.MInquiry;
import com.c.atmpay.Transaksi.ResponInquiry;
import com.c.atmpay.Transaksi.mBankOption;
import com.c.atmpay.Transfer.ModelKonter;
import com.c.atmpay.Transfer.Mtransfer;
import com.c.atmpay.TagihanKonterSales.ResponTagihanKonterSales;
import com.c.atmpay.TransferBank.MTransfer;
import com.c.atmpay.TransferBank.MinquiryBank;
import com.c.atmpay.TransferBank.ModelNamaBank;
import com.c.atmpay.TransferBank.ResponBankSub;
import com.c.atmpay.TransferBank.ResponInquiryBank;
import com.c.atmpay.TransferBank.ResponTransfer;
import com.c.atmpay.konter.DrawSaldo.mDraw;
import com.c.atmpay.konter.KirimSaldo.Mtransfers;
import com.c.atmpay.konter.MRegisKonter;
import com.c.atmpay.konter.MarkupKonter.mMarkKonter;
import com.c.atmpay.konter.MarkupKonter.responMUK;
import com.c.atmpay.konter.Mkonter;
import com.c.atmpay.menuUtama.AngsuranKredit.ResponAngsuran;
import com.c.atmpay.menuUtama.AngsuranKredit.ResponProdukAngsuran;
import com.c.atmpay.menuUtama.BPJS.ResponBPJS;
import com.c.atmpay.menuUtama.DirectLink.mDirectL;
import com.c.atmpay.menuUtama.GasNegara.ResponGasnegara;
import com.c.atmpay.menuUtama.GasNegara.ResponProdukGasnegara;
import com.c.atmpay.menuUtama.HolderPulsa.ResponGroup;
import com.c.atmpay.menuUtama.HolderPulsa.ResponProdukHolder;
import com.c.atmpay.menuUtama.HolderPulsa.ResponSub;
import com.c.atmpay.menuUtama.Internet.ResponIntenet;
import com.c.atmpay.menuUtama.Internet.ResponProdukInternet;
import com.c.atmpay.menuUtama.ListrikPLN.ResponListrikPln;
import com.c.atmpay.menuUtama.ListrikPLNPasca.ResponCodeSub;
import com.c.atmpay.menuUtama.ListrikPLNPasca.ResponGetCodePasca;
import com.c.atmpay.menuUtama.ListrikPLNPasca.ResponListrikPlnPasca;
import com.c.atmpay.menuUtama.PajakPBB.ResponPajak;
import com.c.atmpay.menuUtama.PajakPBB.ResponProdukPBB;
import com.c.atmpay.menuUtama.Paket.ResponPaketData;
import com.c.atmpay.menuUtama.PaketsmsTelpon.ResponProdukSmsTelp;
import com.c.atmpay.menuUtama.PaketsmsTelpon.ResponSmsTelpon;
import com.c.atmpay.menuUtama.PulsaPascaBayar.ResponProdukSubPPasca;
import com.c.atmpay.menuUtama.PulsaPascaBayar.ResponPulsaPasca;
import com.c.atmpay.menuUtama.PulsaPrabayar.MTransaksiPraPulsa;
import com.c.atmpay.menuUtama.PulsaPrabayar.Mchek;
import com.c.atmpay.menuUtama.PulsaPrabayar.ResponPulsaPra;
import com.c.atmpay.menuUtama.PulsaPrabayar.ResponTransaksiPulsaPra;
import com.c.atmpay.menuUtama.TV.ResponProdukTV;
import com.c.atmpay.menuUtama.TV.ResponTV;
import com.c.atmpay.menuUtama.UangElektronik.ResponProdukUE;
import com.c.atmpay.menuUtama.UangElektronik.ResponUangElektronik;
import com.c.atmpay.menuUtama.Voucher.ResponProdukVoucherv;
import com.c.atmpay.menuUtama.Voucher.ResponVoucher;
import com.c.atmpay.menuUtama.VoucherGame.ResponProdukVoucher;
import com.c.atmpay.menuUtama.VoucherGame.ResponVoucherGame;
import com.c.atmpay.menuUtama.air.ResponAir;
import com.c.atmpay.menuUtama.air.ResponProdukAir;
import com.c.atmpay.reseller.ResponApproveSaldoR;
import com.c.atmpay.reseller.ResponSaldoReseller;
import com.c.atmpay.reseller.mSetujuSaldo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("signin")
    Call<Mlogin> Login(@Body Mlogin mlogin);

    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<MRegister> Register(@Body MRegister mRegister);

    @Headers("Content-Type: application/json")
    @POST("otp-email")
    Call<MRegisData> SendOTP(@Body MRegisData mRegisData);

    @Headers("Content-Type: application/json")
    @POST("otp-whatsapp")
    Call<MRegisData> SendOTPWA(@Body MRegisData mRegisData);

    @Headers("Content-Type: application/json")
    @POST("otp-verify")
    Call<MOtpVerif> verifOTP(@Body MOtpVerif mOtpVerif);

    @Multipart
    @POST("ekyc-idcard")
    Call<Responphoto> uploadImage(@Part MultipartBody.Part image, @Part("id") RequestBody id);

    @Multipart
    @POST("ekyc-selfie")
    Call<Responphoto> uploadImageDiri(@Part MultipartBody.Part image, @Part("id") RequestBody id);

    @Multipart
    @POST("photo-upload")
    Call<Responphoto> uploadProfil(@Header("X-Signature") String token,@Part MultipartBody.Part photo, @Part("primary_id") RequestBody id, @Part("type") RequestBody type);

    @POST("transaction")
    Call<ResponTransfer> TransferBank(@Header("X-Signature") String token,
                                      @Body MTransfer mTransfer);

    @GET("product-us/sub-category/{id}")
    Call<ResponGetCodePasca> getCodePLNPasca(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponCodeSub> getCodeSubPln(@Header("X-Signature") String token, @Path("id") String id);

    @Multipart
    @POST("ekyc-idcardselfie")
    Call<Responphoto> uploadImageDiridanKTP(@Part MultipartBody.Part image, @Part("id") RequestBody id);

    @Multipart
    @POST("photo-upload")
    Call<ResponTopUp> uploadBuktiBayar(@Header("X-Signature") String token,@Part MultipartBody.Part image, @Part("type") RequestBody type, @Part("primary_id") RequestBody primary_id);

    @GET("all-province?next=39")
    Call<Respon> getAllProvinsi();

    @GET("profile")
    Call<Mlogin> getProfile(@Header("X-Signature") String token);

    @GET("check-token")
    Call<Mlogin> ChekToken(@Header("X-Signature") String token);

    @GET("banner/serverid/{id}")
    Call<ResponBanner> getBanner(@Header("X-Signature") String token,@Path("id") String id);

    @GET("profile")
    Call<ResponProfil> getProfileDas(@Header("X-Signature") String token);

    @GET("all-direct-link-server")
    Call<mDirectL> getDirect(@Header("X-Signature") String token);

    @GET("all-product-category")
    Call<ResponMenu> getAllProduct(@Header("X-Signature") String token);

    @GET("product-subcategory/category/{id}")
    Call<ResponSub> getSubHolder(@Header("X-Signature") String token, @Path("id") String id);

    @POST("set-pin")
    Call<MsetPIN> SetPIN(@Header("X-Signature") String token, @Body MsetPIN msetPIN);

    @POST("send-saldoku")
    Call<Mtransfer> sendsaldoku(@Header("X-Signature") String token, @Body Mtransfer mtransfer);

    @POST("send-saldoku")
    Call<Mtransfers> sendsaldokuu(@Header("X-Signature") String token, @Body Mtransfers mtransfer);

    @POST("pengajuan-dompet")
    Call<SendPengajuan> SetPengajuanLimit(@Header("X-Signature") String token, @Body SendPengajuan pengajuan);

    @POST("request-paylater")
    Call<SendPengajuan> SetPayLetter(@Header("X-Signature") String token, @Body SendPengajuan pengajuan);

    @GET("request-paylater")
    Call<Responn> GetPayLetter(@Header("X-Signature") String token);

    @GET("request-paylater")
    Call<ResponPengajuan> getPengajuanDompet(@Header("X-Signature") String token);

    @POST("auth-check")
    Call<Mphone> ChekPhone(@Body Mphone mphone);

    @GET("regencies/province/{id}")
    Call<ResponK> getAllKabupaten(@Path("id") long id);

    @GET("districts/regencies/{id}")
    Call<ResponKe> getAllKecamatan(@Path("id") long id);

    @Headers("Content-Type: application/json")
    @GET("province/{id}")
    Call<ResponEditLokasi> getProvinsiByIdd(@Path("id") long id);

    @GET("regencies/{id}")
    Call<ResponKEditKab> getKabupatenById(@Path("id") long id);

    @GET("product-us/sub-category/{id}")
    Call<ResponPulsaPra> getProdukPulsaPraById(@Header("X-Signature") String token, @Path("id") String id);

    @GET("districts/{id}")
    Call<ResponEditKec> getKecamatanById(@Path("id") long id);

    @GET("sub-districts/{id}")
    Call<ResponEditkel> getKelurahanById(@Path("id") long id);

    @GET("postal-code/{id}")
    Call<ResponEditPost> getPostById(@Path("id") long id);

    @GET("product-subcategory/prefix/{id}/{prefix}")
    Call<ResponSubCategory> getSubPrdoductByPrefix(@Header("X-Signature") String token, @Path("prefix") String prefix, @Path("id") String id);

    @GET("sub-districts/districts/{id}")
    Call<Responkel> getAllKelurahan(@Path("id") long id);

    @GET("postal-code/sub-districts/{id}")
    Call<ResponPost> getAllPost(@Path("id") long id);

    @GET("all-product-category?$limit=&$order=urutan&status=1")
    Call<ResponMenuUtama> getAllMenu(@Header("X-Signature") String token);

    @GET("all-product-category?$limit=&$order=urutan&status=1")
    Call<ResponMenuUtama> getAllMenu2(@Header("X-Signature") String token);

    @GET("product-subcategory/category/{id}")
    Call<ResponSubP> getSubCategoryPLN(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/group/{id}")
    Call<ResponListrikPln> getProdukPLNListrik(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponListrikPlnPasca> getProdukPLNListrikPasca(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponVoucherGame> getProdukVoucherGame(@Header("X-Signature") String token, @Path("id") String id);

    @POST("request-saldoku")
    Call<ReqSaldoku> AddRequestSaldoku(@Header("X-Signature") String token, @Body ReqSaldoku saldoku);

    @POST("set-pin")
    Call<MPin> UbahPin(@Header("X-Signature") String token, @Body MPin mPin);

    @POST("inquiry")
    Call<ResponInquiry> CekInquiry(@Header("X-Signature") String token, @Body MInquiry mInquiry);

    @POST("approve-paylater")
    Call<ResponPersetujuan> sendDataPersetujuan(@Header("X-Signature") String token, @Body SendDataPersetujuan sendDataPersetujuan);

    @GET("approve-paylater")
    Call<ResponPersetujuanSaldo> getDataAprroval(@Header("X-Signature") String token);

    @POST("register-konter")
    Call<ResponTambahKonter> registerKonter(@Header("X-Signature") String token, @Body SendDataKonter sendDataKonter);

    @POST("reset-pin")
    Call<ResponResetPin> resetPIN(@Header("X-Signature") String token, @Body MResestPIN mResestPIN);

    @POST("transaction")
    Call<ResponTransaksiPulsaPra> transalsiPulsaPra(@Header("X-Signature") String token, @Body MTransaksiPraPulsa mTransaksiPraPulsa);

    @GET("transaction/status/{id}")
    Call<Mchek> CekTransaksi(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponProdukSmsTelp> getProdukSmsTelpon(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponSmsTelpon> getProdukSMST(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponPaketData> getPaketDataProduk(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/prefix/{id}/{prefix}")
    Call<ResponPulsaPasca> getSubPulsaPascaByPrefix(@Header("X-Signature") String token, @Path("prefix") String prefix, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukSubPPasca> getProdukPulsaPasca(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukVoucher> getProdukVG(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponUangElektronik> getProdukCategoryUE(@Header("X-Signature") String token, @Path("id") String id);

    @GET("transaction/history")
    Call<ResponTransaksi> getHistoriTransaksi(@Header("X-Signature") String token, @Query("start") String start,@Query("end") String end);

    @GET("transaction/history?date=week")
    Call<ResponTransaksiN> getHistoriTransaksiN(@Header("X-Signature") String token);

    @GET("notif-fcm/serverid/{id}")
    Call<mPesan> getHistoriPesanN(@Header("X-Signature") String token, @Path("id") String id);

    @GET("notif-fcm/{id}")
    Call<mPesanDetail> getHistoriPesanNbyID(@Header("X-Signature") String token, @Path("id") String id);

    @GET("transaction/history?date=week")
    Call<ResponStruk> getHistoriStruk(@Header("X-Signature") String token);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukUE> getProdukUE(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponAir> getProdukCategoryAir(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukAir> getProdukAir(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponIntenet> getProdukInternet(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukInternet> getProdukInternetsub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponTV> getProdukTV(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukTV> getProdukTVsub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponVoucher> getProdukVoucher(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukVoucherv> getProdukVoucherSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponBPJS> getProdukBpjs(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponBPJS> getProdukBpjsSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponAngsuran> getProdukAngsuran(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponProdukAngsuran> getProdukAngsuranSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponPajak> getProdukPajak(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponProdukPBB> getProdukPBBSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponGasnegara> getProdukGas(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product/sub-category/{id}")
    Call<ResponProdukGasnegara> getProdukGasSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("all-product-category?$limit=0&$order=urutan&status=1")
    Call<ResponProdukDH> getProdukDH(@Header("X-Signature") String token);

    @GET("all-product-category?$limit=0&$order=urutan&status=1")
    Call<ResponProdukDHM> getProdukDHM(@Header("X-Signature") String token);

    @GET("product-subcategory/category/{id}")
    Call<ResponSubProdukDH> getProdukDHsub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponSubProdukDHM> getProdukDHsubM(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukList> getProdukDHList(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukListM> getProdukDHListM(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us-sm/sub-category/{id}")
    Call<ResponProdukListM> getProdukDHListMM(@Header("X-Signature") String token, @Path("id") String id);

    @POST("inquiry")
    Call<ResponInquiryBank> getInquiryBank(@Header("X-Signature") String token, @Body MinquiryBank minquiryBank);

    @POST("reset-password")
    Call<ResponResetPassword> resetPassword(@Body mResetPassword mResest);

    @GET("user-paylater")
    Call<ResponTagihanPayLatter> getTagihan(@Header("X-Signature") String token);

    @GET("users-referal")
    Call<Mkonter> getKonterSales(@Header("X-Signature") String token);

    @POST("user-paylater-payment")
    Call<ResponUPP> SetUPP(@Header("X-Signature") String token, @Body AddUPP upp);

    @GET("user-paylater-payment")
    Call<ResponTagihanKonter> getTagihanSales(@Header("X-Signature") String token,@Query("start")
            String datestart,@Query("end") String dateend);

    @GET("users-referal")
    Call<ModelKonter> getKonter(@Header("X-Signature") String token);

    @POST("approve-paylater-payment")
    Call<ResponApprove> ApproveTagihan(@Header("X-Signature") String token, @Body SendApprove approve);

    @Headers({"Content-Type: application/json"})
    @PUT("profile")
    Call<ResEdit> editProfil(@Header("X-Signature") String token, @Body MProfilEdit mProfilEdit);

    @PUT("users/markup")
    Call<ResponMarkup> markup(@Header("X-Signature") String token, @Body sendMarkUP markup);

    @GET("sales-komisi/history")
    Call<ResponSales> getKomisiSales(@Header("X-Signature") String token,@Query("datestart")
            String datestart,@Query("dateend") String dateend);

    @GET("user-paylater/sales")
    Call<ResponTagihanKonterSales> getTagihanSalesKonter(@Header("X-Signature") String token);

    @GET("product-us/sub-category/{id}")
    Call<ModelNamaBank> getNamaBank(@Header("X-Signature") String token,@Path("id")String id);

    @GET("history/saldo")
    Call<responRekap> getSaldoRekap(@Header("X-Signature") String token,
                                    @Query("start") String start,@Query("end") String end,
                                    @Query("type") String type);
    @Multipart
    @PUT("product-us-sm/{id}")
    Call<ResponMarkup> markupSpesifik(@Header("X-Signature") String token,@Path("id")String id,@Part("user_id") RequestBody user_id,@Part("server_code") RequestBody server_code
                                      ,@Part("sales_code") RequestBody sales_code,@Part("product_id") RequestBody product_id,
                                      @Part("markup_price") RequestBody markup_price,@Part("status") RequestBody status);

    @GET("approve-saldoku-reseller")
    Call<ResponSaldoReseller> getSaldoReseller(@Header("X-Signature") String token);

    @POST("approve-saldoku-reseller")
    Call<ResponApproveSaldoR> ApproveSaldokuReselesser(@Header("X-Signature") String token, @Body mSetujuSaldo setuju);

    @GET("product/code/{id}")
    Call<ResponCodeSubPS> getSubCodePS(@Header("X-Signature") String token,@Path("id")String id);

    @GET("product-subcategory/category/{id}")
    Call<ResponBankSub> getSubCategoryBank(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/sub-category/{id}")
    Call<ResponProdukHolder> getProdukHolder(@Header("X-Signature") String token, @Path("id") String id);

    @GET("product-us/group/{id}")
    Call<ResponProdukHolder> getProdukHolderSub(@Header("X-Signature") String token, @Path("id") String id);

    @GET("config/serverid/{id}")
    Call<MRuningText> getRunningText(@Header("X-Signature") String token, @Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("register-konter")
    Call<MRegisKonter> RegisterKonter(@Header("X-Signature") String token,@Body MRegisKonter mRegisKonter);

    @PUT("users/downline/{id}")
    Call<responMUK> markupKonter(@Header("X-Signature") String token, @Body mMarkKonter markup, @Path("id") String id);

    @GET("product-group/sub-category/{id}")
    Call<ResponGroup> getProdukGroup(@Header("X-Signature") String token, @Path("id") String id);

    @POST("sales-komisi/withdraw")
    Call<ResponKomisi> withdrawKomisi(@Header("X-Signature") String token, @Body mKomisi komisi);


    @GET("user-history/komisi")
    Call<ResponSales> getKomisiHistory(@Header("X-Signature") String token,@Query("start")
            String datestart,@Query("end") String dateend);

    @POST("draw-saldoku")
    Call<mDraw> drawSaldoku(@Header("X-Signature") String token, @Body mDraw mdraw);

// get All menu Direct API hit

    @GET("all-direct-link")
    Call<mDirect> getDirectMenu(@Header("X-Signature") String token);

    @GET("payment-option-server/serverid/{id}")
    Call<mBankOption> getBankOptionsd(@Header("X-Signature") String token, @Path("id") String id);

    @POST("otp-login-verify")
    Call<mOTP> SetverifyOtp( @Body mOTP mOTP);

    @GET("poin-reward/serverid/{id}")
    Call<mReward> getRewards(@Header("X-Signature") String token, @Path("id") String id);

    @POST("poin-reward-exchange")
    Call<mExcange> tukarPoint(@Header("X-Signature") String token, @Body mExcange Mexchange);

    @GET("request-saldoku")
    Call<ResponHistory> getDataTopup(@Header("X-Signature") String token, @Query("start") String start,@Query("end") String end);

    @POST("request-saldoku-cancel")
    Call<mCancel> CancelTopup(@Header("X-Signature") String token, @Body mCancel cancel);


}
