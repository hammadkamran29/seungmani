/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kakao.kakaostory;

import com.kakao.auth.APIErrorResult;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.http.HttpResponseHandler;
import com.kakao.util.helper.log.Logger;

/**
 * 카카오스토리 API 요청에 대한 응답 handler로, {@link KakaoStoryService}를 호출할 때 넘겨주고 콜백을 받는다.
 * @author MJ
 */
public abstract class KakaoStoryHttpResponseHandler<T> extends HttpResponseHandler<T> {
    /**
     *  카카오계정에 연결한 카카오스토리 사용자가 아니여서 요청이 실패한 경우 호출된다.
     */
    public abstract void onNotKakaoStoryUser();

    /**
     * 세션이 닫혀서 또는 카카오스토리 사용자가 아닌 경우를 제외한 이유로 요청이 실패한 경우 호출된다.
     * 아래 에러 종류에 따라 적절한 처리를 한다.
     * {@link ErrorCode#INVALID_PARAM_CODE}, <br/>
     * {@link ErrorCode#INVALID_SCOPE_CODE}, <br/>
     * {@link ErrorCode#NOT_SUPPORTED_API_CODE}, <br/>
     * {@link ErrorCode#EXCEED_MAX_UPLOAD_SIZE}, <br/>
     * {@link ErrorCode#EXECUTION_TIMED_OUT}, <br/>
     * {@link ErrorCode#EXCEED_MAX_UPLOAD_NUMBER}, <br/>
     * {@link ErrorCode#INVALID_STORY_SCRAP_URL}, <br/>
     * {@link ErrorCode#INVALID_STORY_ACTIVITY_ID}, <br/>
     * {@link ErrorCode#INTERNAL_ERROR_CODE}, <br/>
     * {@link ErrorCode#CLIENT_ERROR_CODE}, <br/>
     * {@link ErrorCode#EXCEED_LIMIT_CODE}, <br/>
     * {@link ErrorCode#KAKAO_MAINTENANCE_CODE}, <br/>
     * @param errorResult 실패한 원인이 담긴 결과
     */
    public abstract void onFailure(final APIErrorResult errorResult);

    @Override
    public void onHttpFailure(final APIErrorResult errorResult) {
        Logger.d("failed : " + errorResult);
        if (errorResult.getErrorCode() == ErrorCode.NOT_EXIST_KAKAOSTORY_USER_CODE) {
            onNotKakaoStoryUser();
        } else {
            onFailure(errorResult);
        }
    }
}
