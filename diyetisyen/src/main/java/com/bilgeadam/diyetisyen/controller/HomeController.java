package com.bilgeadam.diyetisyen.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bilgeadam.diyetisyen.DTO.DiyestisyenDTO;
import com.bilgeadam.diyetisyen.entity.Danisan;
import com.bilgeadam.diyetisyen.entity.Diyetisyen;
import com.bilgeadam.diyetisyen.entity.Program;
import com.bilgeadam.diyetisyen.entity.Role;
import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDns;
import com.bilgeadam.diyetisyen.service.JpaUserDetailsServiceDyt;
import com.bilgeadam.diyetisyen.service.ProgramService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

@Controller
public class HomeController {

	@Autowired
	private	JpaUserDetailsServiceDyt dytService;
	@Autowired
	private JpaUserDetailsServiceDns dnsService;
	
	@Autowired
	private ProgramService prgrmService;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/accessdenied")
	public String accessdenied() {
		return "accessdenied";
	}
	
	@GetMapping("/login")
	public String signin(boolean error,HttpSession session) {
		if(error == true) {
			session.setAttribute("msg", "Hatalı Kullanıcı adı veya Şifre");
		}
		return "signin";
	}
	
	@PostMapping("/dologin")
	public String dologin() {
		
		return "/";
	}
	
	
	@PreAuthorize("hasRole('ROLE_BASDYT')")
	@GetMapping("/dyt_kayit")
	public String kayit_dyt(Model model) {
		
		Diyetisyen d = new Diyetisyen();
		model.addAttribute("diyetisyen",d);
		return "dyt_kayit";
	}
	
	
	@PreAuthorize("hasRole('ROLE_BASDYT')")
	@PostMapping("/dyt_kayit")
	public String doKayit_dyt(@Valid @ModelAttribute("diyetisyen") Diyetisyen d,BindingResult result,HttpSession session){

		if(result.hasErrors()) {
			return "dyt_kayit";
		}
		if(dytService.checkEmail(d.getEmail())) {
			session.setAttribute("msg", "E-mail kayıtlı.");
			return "dyt_kayit";
			}else {
			d.setActive(true);
			d.setUsername(d.getEmail());
			d.setInsertedDate(LocalDateTime.now());
			Role roleDyt = new Role(2,"ROLE_DYT");
			List<Role> roleList= new ArrayList<Role>();
			roleList.add(roleDyt);
			d.setRoles(roleList);
			d.setPassword(encoder.encode(d.getPassword()));
			dytService.add(d);
		}
		return "redirect:/diyetisyenler";
	}
	

	@PreAuthorize("hasRole('ROLE_BASDYT')")
	@GetMapping("/editDyt/{id}")
	public String updateDyt(@PathVariable Integer id, Model model,HttpSession session) {
			Diyetisyen d = null;
		try {
			 d = dytService.getDiyetisyenById(id);
		} catch (Exception e) {
			session.setAttribute("msg", "Öyle bir Diyetisyen yok");
		}
		model.addAttribute("diyetisyen",d);
		return "dyt_update";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT')")
	@PostMapping("/updateDyt/{id}")
	public String doUpdateDyt(@PathVariable Integer id,@ModelAttribute("diyetisyen") Diyetisyen d,HttpSession session){
		Diyetisyen existingDiyetisyen =	dytService.getDiyetisyenById(id);
		
		if(!existingDiyetisyen.getEmail().equals(d.getEmail()) && dytService.checkEmail(d.getEmail())) {
			session.setAttribute("msg", "E-mail kayıtlı.");
			return "dyt_update";
			}else {
				existingDiyetisyen.setFirstName(d.getFirstName());
				existingDiyetisyen.setLastName(d.getLastName());
				existingDiyetisyen.setEmail(d.getEmail());
				existingDiyetisyen.setUsername(d.getEmail());
				existingDiyetisyen.setPhoneNumber(d.getPhoneNumber());
				existingDiyetisyen.setActive(d.getActive());
	
				dytService.update(existingDiyetisyen);
		}
		return "redirect:/diyetisyenler";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT')")
	@GetMapping("/deleteDyt/{id}")
	public String deleteDyt(@PathVariable Integer id,HttpSession session){
		Diyetisyen existingDiyetisyen ;
		try {
			existingDiyetisyen =	dytService.getDiyetisyenById(id);
			dytService.delete(existingDiyetisyen);
		} catch (Exception e) {
			session.setAttribute("msg" , "Diyetisyen bulunamadı");
			return "/diyetisyenler";
		}
		
		return "redirect:/diyetisyenler";
	}
	
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@GetMapping("/danisan_kayit")
	public String danisan_kayit(Model model) {
		Danisan d = new Danisan();
		
		
		model.addAttribute("danisan",d);
		return "danisan_kayit";
	}
	

	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@PostMapping("/danisan_kayit")
	public String doDanisan_kayit(@Valid @ModelAttribute("danisan") Danisan d,BindingResult result,HttpSession session,Authentication authentication){
		String loggedInUserName = ((UserDetails)authentication.getPrincipal()).getUsername();
		
		Diyetisyen dyt = dytService.getUserById(loggedInUserName);
		if(result.hasErrors()) {
			return "danisan_kayit";
		}
		if(dnsService.checkEmail(d.getEmail())) {
			session.setAttribute("msg", "E-mail kayıtlı.");
			return "danisan_kayit";
		}else {
			d.setActive(true);
			d.setUsername(d.getEmail());
			d.setInsertedDate(LocalDateTime.now());
			Role roleDns = new Role(3,"ROLE_DNS");
			List<Role> roleList= new ArrayList<Role>();
			roleList.add(roleDns);
			d.setRoles(roleList);
			d.setPassword(encoder.encode(d.getPassword()));
			d.setInsertedDiyetisyen(dyt);
			dnsService.add(d);
		}	
		return "redirect:/danisanlar";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@GetMapping("/editDns/{id}")
	public String updateDns(@PathVariable Integer id, Model model,HttpSession session) {
			Danisan d = null;
		try {
			 d = dnsService.getDanisanById(id);
		} catch (Exception e) {
			session.setAttribute("msg", "Öyle bir Danisan yok");
		}
		model.addAttribute("danisan",d);
		return "dns_update";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@PostMapping("/updateDns/{id}")
	public String doUpdateDns(@PathVariable Integer id,@ModelAttribute("danisan") Danisan d,HttpSession session){
		Danisan existingDanisan =	dnsService.getDanisanById(id);
		
		if(!existingDanisan.getEmail().equals(d.getEmail()) && dnsService.checkEmail(d.getEmail())) {
			session.setAttribute("msg", "E-mail kayıtlı.");
			return "dns_update";
			}else {
				existingDanisan.setFirstName(d.getFirstName());
				existingDanisan.setLastName(d.getLastName());
				existingDanisan.setEmail(d.getEmail());
				existingDanisan.setUsername(d.getEmail());
				existingDanisan.setPhoneNumber(d.getPhoneNumber());
				existingDanisan.setActive(d.getActive());
				existingDanisan.setAge(d.getAge());
				existingDanisan.setHeight(d.getHeight());
				existingDanisan.setWeight(d.getWeight());
				existingDanisan.setMale(d.getMale());
	
				dnsService.update(existingDanisan);
		}
		return "redirect:/danisanlar";
	}
	

	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@GetMapping("/deleteDns/{id}")
	public String deleteDns(@PathVariable Integer id,HttpSession session){
		Danisan existingDanisan ;
		try {
			existingDanisan =	dnsService.getDanisanById(id);
			dnsService.delete(existingDanisan);
		} catch (Exception e) {
			session.setAttribute("msg" , "Diyetisyen bulunamadı");
			return "/danisanlar";
		}
		
		return "redirect:/danisanlar";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@GetMapping("/dyt_özel")
	public String dyt_özel() {
		return "dyt_özel";
	}
	
	@PreAuthorize("hasRole('ROLE_DNS')")
	@GetMapping("/danisan_özel")
	public String danisan_özel() {
		return "danisan_özel";
	}
	
	@GetMapping("/iletisim")
	public String iletisim() {
		return "iletisim";
	}
	

	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')")
	@GetMapping(path={"/program_dyt","/searchProgramDns"})
	public String program_dyt(Model model ,String keyword) {
		if(keyword == null ) {
			List<Danisan> danisanListesi = dnsService.getAllDanisanlar();
			model.addAttribute("danisanlar", danisanListesi);
		}else {
			model.addAttribute("danisanlar" , dnsService.getDanisanlarFromQuery(keyword));
		}
		return "program_dyt";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')") 
	@GetMapping(path={"/danisanlar","/searchDns"})
	public String danisanlar(Model model ,String keyword) {
		
		if(keyword == null ) {
			List<Danisan> danisanListesi = dnsService.getAllDanisanlar();
			model.addAttribute("danisanlar", danisanListesi);
		}else {
			model.addAttribute("danisanlar" , dnsService.getDanisanlarFromQuery(keyword));
		}
		return "danisanlar";
	}
	@GetMapping("/program_guncelle/{id}")
	public String program_guncelle(Model model,@PathVariable Integer id) {
		Danisan d = dnsService.getDanisanById(id);
		Program p=d.getProgram();
		if(p!=null) {

			model.addAttribute("program",p);
			return "program_guncelle";
		}
		return "redirect:/program_dyt";
	

		
	}
	
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')") 
	@PostMapping("/program_guncelle/{id}")
	public String program_guncelle_post(@PathVariable Integer id,@ModelAttribute("program") Program p,HttpSession session) {
			Program updatedProgram  = dnsService.getDanisanById(id).getProgram();
			updatedProgram.setIcerik1(p.getIcerik1());
			updatedProgram.setIcerik2(p.getIcerik2());
			updatedProgram.setIcerik3(p.getIcerik3());
			updatedProgram.setIcerik4(p.getIcerik4());
			updatedProgram.setIcerik5(p.getIcerik5());
			updatedProgram.setIcerik6(p.getIcerik6());
			updatedProgram.setIcerik7(p.getIcerik7());
			updatedProgram.setIcerik8(p.getIcerik8());
			updatedProgram.setIcerik9(p.getIcerik9());
			updatedProgram.setIcerik10(p.getIcerik10());
			updatedProgram.setIcerik11(p.getIcerik11());
			updatedProgram.setIcerik12(p.getIcerik12());
			updatedProgram.setIcerik13(p.getIcerik13());
			updatedProgram.setIcerik14(p.getIcerik14());
			updatedProgram.setIcerik15(p.getIcerik15());
			updatedProgram.setIcerik16(p.getIcerik16());
			updatedProgram.setIcerik17(p.getIcerik17());
			updatedProgram.setIcerik18(p.getIcerik18());
			updatedProgram.setIcerik19(p.getIcerik19());
			updatedProgram.setIcerik20(p.getIcerik20());
			updatedProgram.setIcerik21(p.getIcerik21());

			prgrmService.update(updatedProgram);

		return "redirect:/program_dyt";
	}
	
	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')") 
	@GetMapping("/program_yaz/{id}")
	public String program_yaz(Model model,@PathVariable Integer id) {
		Program p = new Program();
		p.setDanisan(dnsService.getDanisanById(id));
		
		model.addAttribute("program",p);
		return "program_yaz";
	}

	
	@GetMapping("/program_danisan")
	public String program_goruntule(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserName = ((UserDetails)authentication.getPrincipal()).getUsername();
		Diyetisyen diyetisyen = null;
		Danisan danisan = null;
		Integer userId = null;
		if(dnsService.loadUserByUsername(loggedInUserName)!=null) {
			danisan=dnsService.getUserByUsername(loggedInUserName);
			userId = danisan.getId();
		}
		if(danisan.getProgram()==null) {
			return "index";
		}
		Program p = danisan.getProgram();
		
		
		model.addAttribute("program",p);
		return "program_danisan";
	}

	@PreAuthorize("hasRole('ROLE_BASDYT') or hasRole('ROLE_DYT')") 
	@PostMapping("/program_yaz/{id}")
	public String program_post(@PathVariable Integer id,@ModelAttribute("program") Program p,HttpSession session) {
			
		
			p.setDanisan(dnsService.getDanisanById(id));
			prgrmService.add(p);
			

		return "redirect:/program_dyt";
	}

	@PreAuthorize("hasRole('ROLE_BASDYT')") 
	@GetMapping(path={"/diyetisyenler","/searchDyt"})
	public String diyetisyenler(Model model,String keyword) {
		if(keyword == null ) {
			List<Diyetisyen> diyetisyenListesi = dytService.getAllDiyetisyenler();
			model.addAttribute("diyetisyenler", diyetisyenListesi);
		}else {
			model.addAttribute("diyetisyenler" , dytService.getDiyetisyenlerFromQuery(keyword));
		}
		return "diyetisyenler";
	}
	
	
	
	
	
	
	
	
	
	
}
