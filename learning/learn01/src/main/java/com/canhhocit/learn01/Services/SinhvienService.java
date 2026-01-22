package com.canhhocit.learn01.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canhhocit.learn01.DTO.Request.SinhvienCreationRequest;
import com.canhhocit.learn01.DTO.Request.SinhvienUpdateRequest;
import com.canhhocit.learn01.Entities.Sinhvien;
import com.canhhocit.learn01.Repositories.SinhvienRepository;

@Service
public class SinhvienService {
    @Autowired
    private SinhvienRepository svRepo;

    public Sinhvien createSinhvien(SinhvienCreationRequest request) {
        Sinhvien sv = new Sinhvien();

        if(svRepo.existsBySdt(request.getSdt())){
            throw new RuntimeException("SDT này đã tồn tại");
        }
        
        sv.setHoten(request.getHoten());
        sv.setLop(request.getLop());
        sv.setSdt(request.getSdt());
        return svRepo.save(sv);
    }

    public List<Sinhvien> getAll() {
        return svRepo.findAll();
    }

    public Sinhvien getSVbyMSv(String msv) {
        return svRepo.findById(msv).orElseThrow(() -> new RuntimeException("Sinhvien not exists!!"));
    }

    public Sinhvien updateSV(SinhvienUpdateRequest request, String msv) {
        Sinhvien sv = getSVbyMSv(msv);
        sv.setLop(request.getLop());
        sv.setSdt(request.getSdt());
        return svRepo.save(sv);
    }

    public void deleteSV(String msv){
        svRepo.deleteById(msv);
    }
}
