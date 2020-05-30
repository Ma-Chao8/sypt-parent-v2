package com.tianma315.core.small_code.service.impl;

import com.tianma315.core.CodeRestService;
import com.tianma315.core.HttpResult;
import com.tianma315.core.agent.dao.AgentDao;
import com.tianma315.core.agent.service.AgentAddressService;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.coderecord.dao.CodeMapper;
import com.tianma315.core.coderecord.dao.CodeRecordDao;
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.coderecord.domain.pojo.Code;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.invoice.dao.*;
import com.tianma315.core.invoice.domain.vo.InvoiceProductCodeVO;
import com.tianma315.core.invoice.domain.vo.PackCode;
import com.tianma315.core.invoice.domain.vo.ReturnedProductCodeVO;
import com.tianma315.core.invoice.domain.vo.ScanCodeVO;
import com.tianma315.core.invoice.vo.ScanCodeVo;
import com.tianma315.core.small_code.dao.SmallRecordDao;
import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.small_code.service.CodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * Description
 * <p>
 * Created by zcm on 2019/9/3.
 */
@Service
public class CodeServicesImpl extends CoreServiceImpl<CodeMapper, Code> implements CodeService {
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    SmallRecordDao smallRecordDao;
    @Autowired
    ReturnedProductCodeDao returnedProductCodeDao;
    @Autowired
    InvoiceProductCodeDao invoiceProductCodeDao;
    @Autowired
    private ReturnedDao returnedDao;
    @Autowired
    private ReturnedProductDao returnedProductDao;
    @Autowired
    private AgentDao agentDao;
    @Autowired
    private AgentAddressService agentAddressService;
    @Autowired
    private InvoiceProductDao invoiceProductDao;

    @Override
    public boolean validate(Long companyId, String productIdent, List<ScanCodeVo> codes) {
        allPackCodes(companyId, productIdent, codes);
        return true;
    }

    public boolean allowDelivered(long merchant_id, String product_ident, List<ScanCodeVo> codes) {
        //判定一个码是否允许出库 必须满足以下的条件
        //1、发货单里没有该码的记录(代表当前码已经出库)
        //2、发货单里面有该码的记录  且退货单也有该码的记录  且同属于一个订单(代表当前码已经退货)
        //3、如果只退货一部分  则这个大码不允许再次发货
        if (codes == null || codes.isEmpty()) {
            throw new MessageException("数据不能为空");
        }

        List<String> invoicedCodes = new ArrayList<>();//已经出库的码
        List<PackCode> packCodeList = allPackCodes(merchant_id, product_ident, codes);
        for (ScanCodeVo code : codes) {
            if (code == null)
                continue;

            InvoiceProductCodeVO invoiceProductCodeVo = null;
            String codeStr;
            PackCode packCode = filterPackCode(packCodeList, code);
            if (code.getType() == ScanCodeVo.TYPE_BIG_CODE) {
                codeStr = code.getBigCode();
                //查询当前大码是否已经出库
                invoiceProductCodeVo = getLastInvoice(merchant_id, codeStr);
                if (invoiceProductCodeVo != null && !hasReturned(merchant_id, invoiceProductCodeVo, codeStr)) {
                    //已出库 但未退货
                    continue;
                    //throw new MessageException(String.format("大码 %s 已经出库", codeStr));
                }

                //当前大码未出库 继续判断当前大码下的小码是否有出库
                for (String small_code : packCode.getSmallCodes()) {
                    invoiceProductCodeVo = getLastInvoice(merchant_id, small_code);
                    if (invoiceProductCodeVo != null && !hasReturned(merchant_id, invoiceProductCodeVo, small_code)) {
                        //小码已经出库 但是未退货
                        continue;
                        //throw new MessageException(String.format("%s 的部分小码已经出库，不能使用该码出库", codeStr));
                    }
                }

                invoicedCodes.add(code.getBigCode());
            } else if (code.getType() == ScanCodeVo.TYPE_SMALL_CODE) {
                codeStr = code.getSmallCode();
                //查询当前小码是否出库
                invoiceProductCodeVo = getLastInvoice(merchant_id, codeStr);
                if (invoiceProductCodeVo != null && !hasReturned(merchant_id, invoiceProductCodeVo, codeStr)) {
                    //小码已出库,且未退货
                    continue;
                    //throw new MessageException(String.format("小码 %s 已出库", codeStr));
                } else {
                    //小码未出库 判断当前小码所属大码是否出库
                    String big_code = packCode.getBigCode();
                    invoiceProductCodeVo = getLastInvoice(merchant_id, big_code);
                    if (invoiceProductCodeVo != null) {
                        if (!hasReturned(merchant_id, invoiceProductCodeVo, big_code) && !hasReturned(merchant_id, invoiceProductCodeVo, codeStr)) {
                            //大码已出库,且未退货，同时小码也未退货
                            continue;
                            //throw new MessageException(String.format("%s 所属的大码已经出库", codeStr));
                        }
                    }
                }
                //
                invoicedCodes.add(code.getSmallCode());
            }
        }

        //判断是否
        if (invoicedCodes != null && !invoicedCodes.isEmpty()) {
            throw new MessageException(String.format("%s 已经出库", invoicedCodes), invoicedCodes);
        }

        return true;
    }

    @Override
    public boolean returnable(Long companyId, String productIdent, List<String> codes) {
        if (codes == null || codes.isEmpty())
            throw new MessageException("退货码不能为空");

        List<String> returnedCodes = new ArrayList<>();
        List<String> undeliverCodes = new ArrayList<>();
        List<PackCode> packCodeList = allPackCodesByCodes(companyId, productIdent, codes);

        for (String code : codes) {
            if (StringUtils.isBlank(code)) {
                continue;
            }
            PackCode packCode = filterPackCodeByCode(packCodeList, code);
            String returned_code = code;
            InvoiceProductCodeVO invoiceProductCodeVo = null;
            if (getCodeType(packCode, code) == ScanCodeVo.TYPE_BIG_CODE) {
                //大码退货需要判断该码是否出库, 如果存在出库记录时还须要判断当前码是否退货
                invoiceProductCodeVo = getLastInvoice(companyId, returned_code);
                if (invoiceProductCodeVo != null
                        && hasReturned(companyId, invoiceProductCodeVo, returned_code)) {
                    //出库记录和退货记录同属于几个订单 可视为已经退货
                    returnedCodes.add(code);
                    continue;
                    //throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                }
            } else if (getCodeType(packCode, code) == ScanCodeVo.TYPE_SMALL_CODE) {
                //小码退货- 需要判断当前小码是否已经发货（包扩小码发货或者小码对应的大码发货）
                invoiceProductCodeVo = getLastInvoice(companyId, returned_code);
                if (invoiceProductCodeVo == null) {

                    //小码查不到发货单  使用大码继续查
                    returned_code = packCode.getBigCode();
                    invoiceProductCodeVo = getLastInvoice(companyId, returned_code);

                    if (invoiceProductCodeVo != null) {
                        if (hasReturned(companyId, invoiceProductCodeVo, returned_code)) {
                            //大码已退货
                            returnedCodes.add(code);
                            continue;
                            //throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                        } else {
                            //大码未退货，需要判断当前小码是否退货
                            returned_code = code;
                            if (hasReturned(companyId, invoiceProductCodeVo, returned_code)) {
                                //该小码对应的大码已经有退货记录，且属于同一个订单
                                returnedCodes.add(code);
                                continue;
                                //throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                            }
                        }
                    }
                } else {
                    //小码直接查到了出库记录，说明当前货单是由小码直接发货的
                    String temp_code = packCode.getBigCode();
                    InvoiceProductCodeVO invoiceProductCodeVo1 = getLastInvoice(companyId, temp_code);
                    if (invoiceProductCodeVo1 != null) {
                        //同一套标下的大小码同时存在出库记录，按照系统定义的规则（不能大小码同时出库），必有一个是已经退货的状态
                        //此时需要判定哪一个记录才是有效的

                        if (hasReturned(companyId, invoiceProductCodeVo1, temp_code)) {
                            //大码已经退货
                            if (hasReturned(companyId, invoiceProductCodeVo, returned_code)) {
                                throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                            }
                        } else {
                            //大码未退货
                            if (hasReturned(companyId, invoiceProductCodeVo1, returned_code)) {
                                returnedCodes.add(code);
                                continue;
                                //throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                            }
                            invoiceProductCodeVo = invoiceProductCodeVo1;
                        }
                    } else {
                        //大码不存在出库记录，只需要判断当前小码是否退货即可
                        if (invoiceProductCodeVo != null && hasReturned(companyId, invoiceProductCodeVo, returned_code))
                            returnedCodes.add(code);
                        continue;
                        //throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                    }
                }
            }

            //查不到出库记录
            if (invoiceProductCodeVo == null) {
                undeliverCodes.add(code);
                //throw new MessageException(String.format("%s 尚未出库", code));
            }
        }

        if (undeliverCodes != null && !undeliverCodes.isEmpty()) {
            throw new MessageException(String.format("%s 尚未出库", undeliverCodes), undeliverCodes);
        }

        if (returnedCodes != null && !returnedCodes.isEmpty()) {
            throw new MessageException(String.format("%s 已经完成退货，请勿重复操作", returnedCodes), returnedCodes);
        }

        return true;
    }

    @Override
    public boolean inspectable(Long companyId, String productIdent, List<String> codes) {

        return false;
    }


    /**
     * 筛选符合条件的套标
     *
     * @param packCodes
     * @param codeVo
     * @return
     */
    private PackCode filterPackCode(List<PackCode> packCodes, ScanCodeVo codeVo) {
        String codeStr = null;
        if (codeVo.getType() == ScanCodeVo.TYPE_BIG_CODE) {
            codeStr = codeVo.getBigCode();
            for (PackCode code : packCodes) {
                if (code.getBigCode().equals(codeVo.getBigCode())) {
                    return code;
                }
            }
        } else if (codeVo.getType() == ScanCodeVo.TYPE_SMALL_CODE) {
            codeStr = codeVo.getSmallCode();
            for (PackCode code : packCodes) {
                if (code.getSmallCodes() != null && code.getSmallCodes().contains(codeVo.getSmallCode())) {
                    return code;
                }
            }
        }
        throw new MessageException(String.format("%s 套标获取失败", codeStr));
    }


    private PackCode filterPackCodeByCode(List<PackCode> packCodes, String code) {
        for (PackCode packCode : packCodes) {
            if (packCode.getBigCode().equals(code) || packCode.getSmallCodes().contains(code)) {
                return packCode;
            }
        }
        throw new MessageException(String.format("%s 套标获取失败", code));
    }


    /**
     * @param merchant_id
     * @param product_ident
     * @param codes
     * @return
     */
    private List<PackCode> allPackCodes(long merchant_id, String product_ident, List<ScanCodeVo> codes) {
        List<String> invalidCodes = new ArrayList<>();
        List<PackCode> packCodeList = new ArrayList<>();
        String codeTemp = null;
        if (codes != null) {
            for (ScanCodeVo code : codes) {
                PackCode packCode = null;
                if (code.getType() == ScanCodeVo.TYPE_BIG_CODE) {
                    codeTemp = code.getBigCode();
                } else if (code.getType() == ScanCodeVo.TYPE_SMALL_CODE) {
                    codeTemp = code.getSmallCode();
                }

                packCode = getPackCode(merchant_id, codeTemp, product_ident);

                if (packCode == null) {
                    invalidCodes.add(codeTemp);
                } else {
                    packCodeList.add(packCode);
                }
            }
        }

        if (invalidCodes != null && !invalidCodes.isEmpty()) {
            throw new MessageException(String.format("%s 不是系统有效码", invalidCodes), invalidCodes);
        }
        return packCodeList;
    }


    private List<PackCode> allPackCodesByCodes(long merchant_id, String product_ident, List<String> codes) {
        List<String> invalidCodes = new ArrayList<>();
        List<PackCode> packCodeList = new ArrayList<>();
        if (codes != null) {
            for (String code : codes) {
                PackCode packCode = getPackCode(merchant_id, code, product_ident);
                if (packCode == null) {
                    invalidCodes.add(code);
                } else {
                    packCodeList.add(packCode);
                }
            }
        }

        if (invalidCodes != null && !invalidCodes.isEmpty()) {
            throw new MessageException(String.format("%s 不是系统有效码", invalidCodes), invalidCodes);
        }
        return packCodeList;
    }


    /**
     * 获取套标
     *
     * @return
     */
    public PackCode getPackCode(long companyId, String code, String productIdent) {
        List<SmallRecordDO> codes = null;
        //从数据库表查询码是否存在
        codes = smallRecordDao.selectByCode(companyId, code);
        if (codes == null || codes.isEmpty()) {
            //产品标识为空 可视为不支持接口查询套标
            if (StringUtils.isBlank(productIdent)) {
                return null;
            }

            String[] idents = productIdent.split(",");
            for (String ident : idents) {
                //查询当前码是否是系统的码
                String url = CodeRestService.URL_PACK_CODE
                        .replace(CodeRestService.LOGISTICS_CODE, code)
                        .replace(CodeRestService.PRODUCT_CODE, ident);

                HttpResult<PackCode> result = CodeRestService
                        .getInstance()
                        .get(url, null, new ParameterizedTypeReference<HttpResult<PackCode>>() {
                        });

                if (result.isSuccess()) {
                    return result.getDetails();
                }
            }
            return null;
        }
        //构建套标数据结构
        return listToPackCode(codes, 0);
    }

    /**
     * 查询数据库列表转换为套标
     *
     * @param codes
     * @param index
     * @return
     */
    private PackCode listToPackCode(List<SmallRecordDO> codes, int index) {
        if (index == codes.size()) {
            return new PackCode();
        }

        PackCode packCode = listToPackCode(codes, index + 1);
        SmallRecordDO code = codes.get(index);

        if (StringUtils.isBlank(packCode.getBigCode())) {
            packCode.setBigCode(code.getBigCode());
        }
        if (packCode.getBigCode().equals(code.getBigCode())) {
            if (packCode.getSmallCodes() == null) {
                packCode.setSmallCodes(new ArrayList<>());
            }
            packCode.getSmallCodes().add(code.getSmallCode());
        }
        return packCode;
    }


    /**
     * 判断当前码是否已经退货
     *
     * @param companyId
     * @param invoice
     * @param code
     * @return
     */
    private boolean hasReturned(long companyId, InvoiceProductCodeVO invoice, String code) {
        if (invoice == null || StringUtils.isBlank(code))
            return false;
        ReturnedProductCodeVO returnedProductCodeVo = getLastReturned(companyId, invoice.getInvoiceId(), code);
        return returnedProductCodeVo != null;
    }

    /**
     * 最新退货的订单
     *
     * @param companyId
     * @param code
     * @return
     */
    private ReturnedProductCodeVO getLastReturned(long companyId, long invoiceId, String code) {
        return returnedProductCodeDao.selectLastReturnedByCode(companyId, invoiceId, code);
    }

    /**
     * 最新发货的订单
     *
     * @param companyId
     * @param code
     * @return
     */
    private InvoiceProductCodeVO getLastInvoice(long companyId, String code) {
        return invoiceProductCodeDao.selectLastDeliverByCode(companyId, code);
    }

    /**
     * 获取码类型
     *
     * @param packCode
     * @param code
     * @return
     */
    private int getCodeType(PackCode packCode, String code) {

        if (packCode == null || StringUtils.isBlank(code))
            return -1;

        if (code.equals(packCode.getBigCode())) {
            return ScanCodeVO.TYPE_BIG_CODE;
        } else if (packCode.getSmallCodes().contains(code)) {
            return ScanCodeVO.TYPE_SMALL_CODE;
        }
        return -1;
    }


}
