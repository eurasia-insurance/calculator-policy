package kz.theeurasia.policy.calc.bean.holder;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.policy.Policy;

import kz.theeurasia.policy.calc.api.PolicyHolder;

@Named("policy")
@ViewScoped
public class DefaultPolicyHolder extends DefaultValueHolder<Policy> implements PolicyHolder {
    private static final long serialVersionUID = -2858023308637177175L;
}
