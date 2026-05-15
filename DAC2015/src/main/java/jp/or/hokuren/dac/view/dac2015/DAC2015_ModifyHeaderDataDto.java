/*
 * ClassName   DAC2015_ModifyHeaderDataDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 ヘッダデータ修正Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 人件費情報入力 ヘッダデータ修正Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_ModifyHeaderDataDto {

  /** 支給日 */
  private LocalDate shikyubi;

  /** 入力区分コード */
  private String nyuryokuKbnCd;

  /** 伝票番号 */
  private String dempyoNo;

  /** 伝票番号（給与控除） */
  private String dempyoNoKyuyoKojo;

  /** 申請番号 */
  private String shinseiNo;

  /** 申請番号（給与控除） */
  private String shinseiNoKyuyoKojo;

  /** 承認状況区分 */
  private String shoninJokyoKbn;

  /** 添付ファイルＩＤ１ */
  private Integer tenpuFileId1;

  /** 添付ファイルＩＤ２ */
  private Integer tenpuFileId2;

  /** 添付ファイルＩＤ３ */
  private Integer tenpuFileId3;

  /** 参照URL */
  private String sanshoUrl;

  /** 排他カウンタ */
  private Integer haitaCnt;

  /** 排他カウンタ（給与控除） */
  private Integer haitaCntKyuyoKojo;
}
