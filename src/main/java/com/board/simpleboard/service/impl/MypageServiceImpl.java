package com.board.simpleboard.service.impl;

import com.board.simpleboard.repository.MypageRepository;
import com.board.simpleboard.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
    private final MypageRepository mypageRepository;

    @Override
    public boolean updateNickname(String nickname, Long id) {

        boolean result = mypageRepository.findByNickname(nickname);
        if (result) {
            mypageRepository.updateNickname(nickname, id);
        }
        return result;
    }
}
