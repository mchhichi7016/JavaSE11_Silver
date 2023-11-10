package ecsite.ex.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ecsite.ex.models.entity.AdminEntity;
import ecsite.ex.models.entity.ProductEntity;
import ecsite.ex.services.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private HttpSession session;
	
	//商品一览
	@GetMapping("/product/list")
	public String getProductList(Model model) {
		//セッションからログインしている人の情報を取得
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
			List<ProductEntity>productList = productService.selectAll(admin.getAdminId());
				model.addAttribute("productList", productList);
				model.addAttribute("adminName", admin.getAdminName());
				return "product_list.html";
			}
		}
	
	//商品登记页面表示
	@GetMapping("/product/register")
	public String getProductRegisterPage(Model model) {
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
				model.addAttribute("adminName", admin.getAdminName());
				return "product_register.html";
			}
	}
	
	//商品登记的方法
	@PostMapping("/product/register/process")
	public String productRegister(@RequestParam String productName,
			@RequestParam String productCategory,
			@RequestParam MultipartFile productImage,
			@RequestParam String productDescription) {
				
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
			//文件名 取得
			/**現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入**/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + productImage.getOriginalFilename();
			try {
				Files.copy(productImage.getInputStream(),Path.of("src/main/resources/static/product-img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(productService.createProduct(productCategory, productDescription, fileName, productName)) {
				return "redirect:/product/list";
			}else {
				return "redirect:/product/register";
			}
			
		}
		
	}
	@GetMapping("/product/edit/{productId}")
	public String getProductEditPage(@PathVariable Long productId, Model model) {
		AdminEntity admin = (AdminEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
				//model.addAttribute("adminName", admin.getAdminName());
				ProductEntity productList = productService.getProductPost(productId);
				if(productList == null) {
					return "redirect:/product/list";
				}else {
					model.addAttribute("productList", productList);
				}
				return "product_edit.html";
			}
	}	
}
