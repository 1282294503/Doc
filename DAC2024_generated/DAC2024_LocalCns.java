/*
 * ClassName   DAC2024_LocalCns
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 ローカル定数
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

/**
 * <pre>
 * 手数料配分計算書出力 ローカル定数
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public class DAC2024_LocalCns {

  /** 審査課コード (TODO: DAA_CommonsCns に移動を検討) */
  public static final String SHINSAKA_CD = "4121";

  /** 参照権限区分.経理部 */
  public static final String SANSHO_KENGEN_KEIRIBU = "1";

  /** 参照権限区分.経理部以外 */
  public static final String SANSHO_KENGEN_NOT_KEIRIBU = "2";

  /** 出力帳票.全道共計手数料配分計算書 */
  public static final String OUTPUT_CHOHYO_ID_ZENDO_KYOKEI_TESURYO_HAIBUN_KEISANSHO = "1";

  /** 出力帳票.購買手数料配分計算書 */
  public static final String OUTPUT_CHOHYO_ID_KOBAI_TESURYO_HAIBUN_KEISANSHO = "2";

  /** CSVレポートID.全道共計手数料配分計算書 */
  public static final String CSV_REPORT_ID_ZENDO_KYOKEI_TESURYO_HAIBUN_KEISANSHO = "DAC20241";

  /** CSVレポートID.購買手数料配分計算書 */
  public static final String CSV_REPORT_ID_KOBAI_TESURYO_HAIBUN_KEISANSHO = "DAC20242";
}
