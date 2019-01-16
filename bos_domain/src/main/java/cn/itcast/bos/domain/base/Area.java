package cn.itcast.bos.domain.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * @description:地域信息实体类，主要包含 省市区(县)
 */
@Entity
@Data
@Table(name = "T_AREA")
@XmlRootElement(name = "area")
public class Area implements Serializable{

	@Id
	@Column(name = "C_ID",unique = true,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO,generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	@Column(name = "C_PROVINCE")
	private String province; // 省
	@Column(name = "C_CITY")
	private String city; // 城市
	@Column(name = "C_DISTRICT")
	private String district; // 区域
	@Column(name = "C_POSTCODE")
	private String postcode; // 邮编
	@Column(name = "C_CITYCODE")
	private String citycode; // 城市编码
	@Column(name = "C_SHORTCODE")
	private String shortcode; // 简码

	@OneToMany(mappedBy = "area")
	private Set<SubArea> subareas = new HashSet<SubArea>();

}
