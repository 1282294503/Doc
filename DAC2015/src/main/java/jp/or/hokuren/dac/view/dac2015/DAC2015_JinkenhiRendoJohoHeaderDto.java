/*
 * ClassName   DAC2015_JinkenhiRendoJohoHeaderDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費連動情報ヘッダDto
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
 * 人件費連動情報ヘッダDto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_JinkenhiRendoJohoHeaderDto {

  /** 排他カウンタ */
  private Integer haitaCnt;

  /** 最終更新ユーザＩＤ */
  private String lstupdUserId;

  /** 支給日 */
  private LocalDate shikyubi;

  /** 入力区分 */
  private String nyuryokuKbn;

  /** 伝票番号 */
  private String dempyoNo;

  /** 承認状況区分 */
  private String shoninJokyoKbn;

  /** 入力ユーザＩＤ */
  private String nyuryokuUserId;

  /** 添付ファイルＩＤ１ */
  private Integer tenpuFileId1;

  /** 添付ファイルＩＤ２ */
  private Integer tenpuFileId2;

  /** 添付ファイルＩＤ３ */
  private Integer tenpuFileId3;

  /** 参照URL */
  private String sanshoUrl;

  /** 申請番号 */
  private String shinseiNo;

  /** 申請依頼者ユーザＩＤ */
  private String shoninIraishaUserId;
}
