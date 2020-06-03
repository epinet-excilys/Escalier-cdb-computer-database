package fr.excilys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ValidatorException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.validator.Validator;

@Service
public class EditComputerService {
	
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private CompanyMapper companyMapper;
	private Validator validator;
	
	public EditComputerService(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper, Validator validator) {
		
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.validator = validator;
	}
	
	public List<CompanyDTO> companyDTOListToDisplay (){
		
		List<Company> companyList = companyService.getAllCompany();
		List<CompanyDTO> companyDTOList = new ArrayList<>();
		
		companyList.stream()
				.forEach(company -> companyDTOList.add(companyMapper.fromCompanyToCompanyDTO(company)));;
		
		return companyDTOList;
	}
	
	public ComputerDTO computerDTOtoDisplayForEdit (String computerId) {
		
		int computerToEditID = Integer.parseInt(computerId);
		ComputerDTO computerDTO = computerMapper.fromComputerToComputerDTO(
				computerService.findByID(computerToEditID).get());
		
		return computerDTO;
		
	}

	public void editComputerInService(String computerId,String computerName,
			String introduced,String discontinued, String companyId) throws ValidatorException
	{
		validator.validateFields(computerName, introduced, discontinued, companyId);

		CompanyDTO companyDTO = new CompanyDTO();
		if(!companyId.isBlank() && !companyId.isEmpty() ) {
			companyDTO.setId(Integer.parseInt(companyId));
		}
		ComputerDTO computerDTO = new ComputerDTO(computerName, introduced, discontinued, companyDTO);
		computerDTO.setId(Integer.parseInt(computerId));

		Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);
		computerService.update(computer);
	}

}
