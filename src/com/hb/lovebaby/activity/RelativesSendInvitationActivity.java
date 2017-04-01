package com.hb.lovebaby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class RelativesSendInvitationActivity extends BascActivity {
	
	private TextView invitationCode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatives_send_invitation);
		
		initViews();
	}

	private void initViews() {
		// Invitation code
		invitationCode = (TextView) findViewById(R.id.invitation_code);
		invitationCode.setText("123456789");
		
		// Distinguish invite other and invite mother/fater
		View otherDesc = findViewById(R.id.invite_other_desc);
		View parentDesc = findViewById(R.id.invite_parent_desc);
		TextView inviteFootNote = (TextView) findViewById(R.id.send_invitation_foot_note);
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if ("mother".equals(type) || "father".equals(type)) {
			otherDesc.setVisibility(View.GONE);
			if ("mother".equals(type)) {
				inviteFootNote.setText(getResources().getString(R.string.invite_foot_note_mother));
			} else {
				inviteFootNote.setText(getResources().getString(R.string.invite_foot_note_father));
			}
		} else {
			parentDesc.setVisibility(View.GONE);
			inviteFootNote.setVisibility(View.GONE);
		}
	}
}
