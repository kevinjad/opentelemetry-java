/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.sdk.metrics;

import io.opentelemetry.api.metrics.LongSumObserver;
import io.opentelemetry.sdk.metrics.AbstractAsynchronousInstrument.AbstractLongAsynchronousInstrument;
import io.opentelemetry.sdk.metrics.common.InstrumentType;
import io.opentelemetry.sdk.metrics.common.InstrumentValueType;
import javax.annotation.Nullable;

final class LongSumObserverSdk extends AbstractLongAsynchronousInstrument
    implements LongSumObserver {
  LongSumObserverSdk(
      InstrumentDescriptor descriptor,
      InstrumentAccumulator instrumentAccumulator,
      @Nullable Callback<LongResult> metricUpdater) {
    super(descriptor, instrumentAccumulator, metricUpdater);
  }

  static final class Builder
      extends AbstractAsynchronousInstrument.Builder<LongSumObserverSdk.Builder>
      implements LongSumObserver.Builder {

    @Nullable private Callback<LongResult> callback;

    Builder(
        String name,
        MeterProviderSharedState meterProviderSharedState,
        MeterSharedState meterSharedState,
        MeterSdk meterSdk) {
      super(name, meterProviderSharedState, meterSharedState, meterSdk);
    }

    @Override
    Builder getThis() {
      return this;
    }

    @Override
    public Builder setCallback(Callback<LongResult> callback) {
      this.callback = callback;
      return this;
    }

    @Override
    public LongSumObserverSdk build() {
      InstrumentDescriptor instrumentDescriptor =
          getInstrumentDescriptor(InstrumentType.SUM_OBSERVER, InstrumentValueType.LONG);
      LongSumObserverSdk instrument =
          new LongSumObserverSdk(instrumentDescriptor, getBatcher(instrumentDescriptor), callback);
      return register(instrument);
    }
  }
}
