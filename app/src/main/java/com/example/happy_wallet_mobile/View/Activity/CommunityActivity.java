package com.example.happy_wallet_mobile.View.Activity; // Đảm bảo đúng package của bạn

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.happy_wallet_mobile.R; // Đảm bảo đúng package của bạn
import com.example.happy_wallet_mobile.View.Fragment.Community.AddNewsFragment;
import com.example.happy_wallet_mobile.View.Fragment.Community.CurrentNewsFragment; // Import CurrentNewsFragment
import com.example.happy_wallet_mobile.View.Fragment.Community.NewsCommentFragment;

public class CommunityActivity extends AppCompatActivity {

    private ImageView ivBack;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community); // Sử dụng layout activity_community.xml

        ivBack = findViewById(R.id.ivBack); // Khởi tạo nút back
        fragmentManager = getSupportFragmentManager();

        // Mặc định hiển thị CurrentNewsFragment khi Activity được tạo
        if (savedInstanceState == null) {
            loadFragment(new CurrentNewsFragment(), false); // false = không thêm vào back stack
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý nút back: nếu có Fragment trong back stack thì pop, ngược lại thì kết thúc Activity
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish(); // Kết thúc Activity
                }
            }
        });
    }


    public void loadFragment(androidx.fragment.app.Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContentContainer, fragment); // flContentContainer là ID của FrameLayout trong activity_community.xml
        if (addToBackStack) {
            transaction.addToBackStack(null); // Sử dụng null cho tên mặc định
        }
        transaction.commit();
    }

    // Phương thức để chuyển từ CurrentNewsFragment sang AddNewsFragment
    public void navigateToAddNews() {
        loadFragment(new AddNewsFragment(), true); // Thêm vào back stack để có thể quay lại feed
    }

    // Phương thức để chuyển từ CurrentNewsFragment sang NewsCommentFragment (khi click vào một post)
    public void navigateToNewsComment(int postId) {
        NewsCommentFragment newsCommentFragment = new NewsCommentFragment();
        Bundle args = new Bundle();
        args.putInt("postId", postId); // Truyền ID của bài đăng
        newsCommentFragment.setArguments(args);
        loadFragment(newsCommentFragment, true);
    }
}