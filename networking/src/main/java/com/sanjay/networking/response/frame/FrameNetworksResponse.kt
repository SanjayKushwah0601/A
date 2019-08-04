package com.sanjay.networking.response.frame

import com.sanjay.networking.response.model.WifiNetwork

class FrameNetworksResponse(status: String, response: List<WifiNetwork>): BaseFrameResponse<List<WifiNetwork>>(status, response)