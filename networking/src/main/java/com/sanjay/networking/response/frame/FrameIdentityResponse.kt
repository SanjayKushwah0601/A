package com.sanjay.networking.response.frame

import com.sanjay.networking.response.model.Frame

class FrameIdentityResponse(status: String, response: Frame): BaseFrameResponse<Frame>(status, response)